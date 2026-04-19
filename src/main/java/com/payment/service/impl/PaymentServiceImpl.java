package com.payment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.payment.constant.Constants;
import com.payment.constant.ErrorCodeEnum;
import com.payment.constant.TransactionStatusEnum;
import com.payment.dto.InitiatePaymentDTO;
import com.payment.dto.PaymentResDTO;
import com.payment.dto.TransactionDTO;
import com.payment.exception.ProcessingException;
import com.payment.http.HttpRequest;
import com.payment.http.HttpServiceEngine;
import com.payment.repository.TransactionDao;
import com.payment.service.PaymentService;
import com.payment.service.PaymentStatusService;
import com.payment.stripeprovider.CreatePaymentReq;
import com.payment.stripeprovider.LineItems;
import com.payment.stripeprovider.PaymentRes;
import com.payment.stripeprovider.SPErrorResponse;
import com.payment.util.GsonUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
	
	private final PaymentStatusService paymentStatusService;
	private final HttpServiceEngine httpServiceEngine;
	private final Gson gson;
	
	private final TransactionDao transactionDao;
	private final ModelMapper modelMapper;
	private final GsonUtils gsonUtils;
	
	@Value("${stripe.provider.create.payment.url}")
	private String stripeProviderCreatePaymentUrl;
	
	@Override
	public TransactionDTO CreatePayment(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stre
		
		//Make complete transactiondto first
		transactionDTO.setTxnStatus(TransactionStatusEnum.CREATED.getName());
		
		transactionDTO.setTxnReference(getTransactionReference());
		
		
		//Logic to save transactionDTO in DB with status: Created;
		TransactionDTO txnDTO = paymentStatusService.processStatus(transactionDTO);
		
		
		log.info("PaymentServiceImpl.CreatePayment transaction dto: " + txnDTO);
		return txnDTO;
	}

	public String getTransactionReference() {
		String transactionReference = UUID.randomUUID().toString();
		log.info("Create transaction reference, txnReference=" + transactionReference);
		return transactionReference;
	}

	@Override
	public TransactionDTO InitiatePayment(String txnReference, InitiatePaymentDTO reqDto) {
		log.info("Initiating payment txnReference:{}|reqDto:{}",
				txnReference, reqDto);

		// Load Txn from DB based on txnReference.
		TransactionDTO txnDTO = transactionDao.getTransactionByReference(txnReference);
		log.info("txnDto from DB:" + txnDTO);
		
		if(txnDTO == null) {
			throw new ProcessingException(
					ErrorCodeEnum.INVALID_TXN_REFERENCE.getErrorCode(), 
					ErrorCodeEnum.INVALID_TXN_REFERENCE.getErrorMessage(),
					HttpStatus.BAD_REQUEST);
		}
		
		txnDTO.setTxnStatus(TransactionStatusEnum.INITIATED.getName());
		paymentStatusService.processStatus(txnDTO);
		//make api call to stripe provider service
		

		HttpRequest httpRequest = prepareHttpCall();
		log.info("Prepared http request, now http call->: {}", httpRequest);
		
		try {
			// Make API call to stripe provider service. RestClient
			ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(httpRequest);
			
			PaymentResDTO paymentResponse = processResponse(httpResponse);
			
			//success update to PENDING
			txnDTO.setTxnStatus(TransactionStatusEnum.PENDING.getName());
			txnDTO.setProviderReference(paymentResponse.getId());
			txnDTO.setUrl(paymentResponse.getUrl());
			paymentStatusService.processStatus(txnDTO);
			log.info("Successfully got url & updated in DB:" + txnDTO);

			return txnDTO;
		} catch (ProcessingException e) {
			// FAILED update to FAILED
			
			txnDTO.setTxnStatus(TransactionStatusEnum.FAILED.getName());
			txnDTO.setErrorCode(e.getErrorCode());
			txnDTO.setErrorMessage(e.getErrorMessage());
			paymentStatusService.processStatus(txnDTO);
			
			if(e.getErrorCode().equals(Constants.STRIPE_PSP_ERROR)) {
				log.error("Got error with StripePSP, so returning standard error to invoker");
				throw new ProcessingException(
						ErrorCodeEnum.ERROR_AT_STRIPE_PSP.getErrorCode(), 
						ErrorCodeEnum.ERROR_AT_STRIPE_PSP.getErrorMessage(), 
						e.getHttpStatus());
			}
			
			throw e;// re-throwing exception for error response.
		}
		
	}

	private PaymentResDTO processResponse(ResponseEntity<String> httpResponse) {
		if(httpResponse.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) {
			// convert to success PaymentRes object. 
			
			PaymentRes spPaymentRes = gsonUtils.fromJson(httpResponse.getBody(), PaymentRes.class);
			log.info("Converted to PaymentRes:" + spPaymentRes);

			if (spPaymentRes != null && spPaymentRes.getUrl() != null) {
				PaymentResDTO paymentResDTO = modelMapper.map(spPaymentRes, PaymentResDTO.class);
				log.info("Converted PaymentRes to PaymentResDTO:" + paymentResDTO);
				return paymentResDTO;
			}
			log.error("GOT 201 but no url in response.");
		}
		
		// convert to error object.
		SPErrorResponse errorResponse = gsonUtils.fromJson(httpResponse.getBody(), SPErrorResponse.class);
		log.error("Converted to SPErrorResponse:" + errorResponse);
		
		if (errorResponse != null && errorResponse.getErrorCode() != null) {
			throw new ProcessingException(
					errorResponse.getErrorCode(), 
					errorResponse.getErrorMessage(), 
					HttpStatus.valueOf(httpResponse.getStatusCode().value()));
		}
		
		log.error("Raising Generic error. Unable to get valid error object structure");
		throw new ProcessingException(
				ErrorCodeEnum.GENERIC_ERROR.getErrorCode(), 
				ErrorCodeEnum.GENERIC_ERROR.getErrorMessage(), 
				HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	public HttpRequest prepareHttpCall() {
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add(httpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		//creating line items object and setting its value
		LineItems lineItem=new LineItems();
		lineItem.setCurrency("EUR");
		lineItem.setProductName("mechanical keyboards");
		lineItem.setQuantity(5);
		lineItem.setUnitAmount(1000);
		
		//Creating list of line items
		List<LineItems> lineItems=new ArrayList<>();
		lineItems.add(lineItem);
		
		//TODO: this dummy data structure will replace with functional data
		//Creating createpaymentrequest object and setting parameters
		CreatePaymentReq createPaymentReq=new CreatePaymentReq();
		createPaymentReq.setSuccessUrl("http://example.com/createPaymentRequest");
		createPaymentReq.setCancelUrl("http://example.com/createPaymentRequest");
		createPaymentReq.setLineItem(lineItems);
		
		//Object requestBody=null ;
		return HttpRequest.builder()
							.method(HttpMethod.POST)
						    .url("http://localhost:8083/v1/payments")
						    .headers(httpHeaders)
						    .requestBody(gson.toJson(createPaymentReq))
						    .build();
		
	}

}
