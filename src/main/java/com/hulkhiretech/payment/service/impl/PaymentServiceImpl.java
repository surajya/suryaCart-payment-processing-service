package com.hulkhiretech.payment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hulkhiretech.payment.constant.TransactionStatusEnum;
import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.http.HttpRequest;
import com.hulkhiretech.payment.http.HttpServiceEngine;
import com.hulkhiretech.payment.pojo.CreatePaymentRequest;
import com.hulkhiretech.payment.service.interfaces.PaymentService;
import com.hulkhiretech.payment.service.interfaces.PaymentStatusService;
import com.hulkhiretech.payment.stripeprovider.CreatePaymentReq;
import com.hulkhiretech.payment.stripeprovider.LineItems;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
	
	private final PaymentStatusService paymentStatusService;
	private final HttpServiceEngine httpServiceEngine;
	private final Gson gson;
	

	@Override
	public String CreatePayment(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stre
		
		//Make complete transactiondto first
		transactionDTO.setTxnStatus(TransactionStatusEnum.CREATED.getName());
		
		transactionDTO.setTxnReference(getTransactionReference());
		
		
		//Logic to save transactionDTO in DB with status: Created;
		paymentStatusService.processStatus(transactionDTO);
		
		//prepare needed to save txn to DB
		
		//Add repository / dto layer & call methods here
		
		log.info("PaymentServiceImpl.CreatePayment");
		return "return from PaymentServiceImpl.CreatePayment";
	}

	public String getTransactionReference() {
		String transactionReference = UUID.randomUUID().toString();
		log.info("Create transaction reference, txnReference=" + transactionReference);
		return transactionReference;
	}

	@Override
	public String InitiatePayment() {
		// TODO Auto-generated method stub
		
		
		TransactionDTO txnDTO = new TransactionDTO();
		//update db with intiated statuse
		//make api call to stripe provider service
		//if success response then staus pending
		//if failure response then staus failed
		
		txnDTO.setTxnStatus(TransactionStatusEnum.INITIATED.getName());
		paymentStatusService.processStatus(txnDTO);
		//make api call to stripe provider service
		

		HttpRequest httpRequest = prepareHttpCall();
		log.info("Prepared http request, now http call->: {}", httpRequest);
		
		ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(httpRequest);
		log.info("response from stripe-service-provider || httpresponse: {}", httpResponse);
		
		//PENDING update payment
		txnDTO.setTxnStatus(TransactionStatusEnum.PENDING.getName());
		txnDTO.setProviderReference("From stripe provider service");
		paymentStatusService.processStatus(txnDTO);
		
		//Failed to update payment
		txnDTO.setTxnStatus(TransactionStatusEnum.PENDING.getName());
		txnDTO.setProviderReference("From stripe provider service");
		txnDTO.setErrorCode("stripe provider service error code");
		txnDTO.setErrorMessage("stripe provider service error message");
		paymentStatusService.processStatus(txnDTO);
		
		return null;
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
		HttpRequest httpRequest=HttpRequest.builder()
							.method(HttpMethod.POST)
						    .url("http://localhost:8083/v1/payments")
						    .headers(httpHeaders)
						    .requestBody(gson.toJson(createPaymentReq))
						    .build();
		return httpRequest;
	}

}
