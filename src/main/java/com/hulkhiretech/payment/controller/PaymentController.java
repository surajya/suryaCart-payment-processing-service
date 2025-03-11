package com.hulkhiretech.payment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payment.constant.TransactionStatusEnum;
import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.pojo.CreatePaymentRequest;
import com.hulkhiretech.payment.pojo.CreatePaymentResponse;
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
		
		String createPayment = paymentService.CreatePayment(txnDTO);
		
		
		//TODO This response is hardcoded ! it temporarily
		CreatePaymentResponse createPaymentresponse = new CreatePaymentResponse();
		createPaymentresponse.setTxnReference("TXN001324");
		log.info("Controller || create payment transaction: "+createPaymentresponse.getTxnReference()+" | createPayment: "+createPayment);
		log.info("Controller||create payment transaction:{}|createPayment:{} ",createPaymentresponse.getTxnReference(), createPayment);
		
		return new ResponseEntity<>(createPaymentresponse,HttpStatus.CREATED);
	}
	
	@PostMapping(value ="/{txnReference}/initiate")
	public ResponseEntity<String> initiatePayment(@PathVariable String txnReference){
		log.info("payments initiated of txnReference: " + txnReference);
		
		
		String initiatePayment = paymentService.InitiatePayment();
		
		return new ResponseEntity<>("initiate payment successfully",HttpStatus.OK);
	}
}
