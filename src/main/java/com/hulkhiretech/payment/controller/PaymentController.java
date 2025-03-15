package com.hulkhiretech.payment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payment.dto.InitiatePaymentDTO;
import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.pojo.CreatePaymentRequest;
import com.hulkhiretech.payment.pojo.CreatePaymentResponse;
import com.hulkhiretech.payment.pojo.InitPaymentResponse;
import com.hulkhiretech.payment.pojo.InitiatePaymentReq;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/payments")
@Slf4j
public class PaymentController {
	
	private ModelMapper modelMapper;
	
	private PaymentService paymentService;
	
	PaymentController(ModelMapper modelMapper, PaymentService paymentService) {
		this.modelMapper = modelMapper;
		this.paymentService = paymentService;
	}
	
	
	@PostMapping
	public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
		log.info("create payment controller, CreatePaymentRequest: " + createPaymentRequest);
		
		//Convert pojo into DTO class
		TransactionDTO txnDTO = modelMapper.map(createPaymentRequest, TransactionDTO.class);
		log.info("DTO created successfully :"+txnDTO);
		
		TransactionDTO transactionDTO = paymentService.CreatePayment(txnDTO);
		
		
		//TODO This response is hardcoded ! it temporarily
		CreatePaymentResponse createPaymentResponse = new CreatePaymentResponse();
		createPaymentResponse.setTxnReference(transactionDTO.getTxnReference());
		createPaymentResponse.setTxnStatus(transactionDTO.getTxnStatus());
		log.info("Controller || create payment transaction: "+createPaymentResponse.getTxnReference()+" | createPayment: "+createPaymentResponse);
		log.info("Controller||create payment transaction:{}|createPayment:{} ",createPaymentResponse.getTxnReference(), createPaymentResponse);
		
		return new ResponseEntity<>(createPaymentResponse,HttpStatus.CREATED);
	}
	
	@PostMapping(value ="/{txnReference}/initiate")
	public ResponseEntity<InitPaymentResponse> initiatePayment(@PathVariable String txnReference, @RequestBody InitiatePaymentReq initiatePaymentReq){
		log.info("Payment status fetched successfully||txnReference:{}|initiatePaymentReq:{}", 
				txnReference, initiatePaymentReq);
		
		InitiatePaymentDTO reqDto = modelMapper.map(initiatePaymentReq, InitiatePaymentDTO.class);
		
		TransactionDTO responseDTO = paymentService.InitiatePayment(txnReference, reqDto);
		
		InitPaymentResponse response = InitPaymentResponse.builder()
				.txnReference(responseDTO.getTxnReference())
				.txnStatus(responseDTO.getTxnStatus())
				.url(responseDTO.getUrl())
				.build();
		log.info("Retunring response:{}", response);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
