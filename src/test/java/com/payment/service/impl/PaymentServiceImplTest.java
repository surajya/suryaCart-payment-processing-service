package com.payment.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.payment.constant.ErrorCodeEnum;
import com.payment.constant.TransactionStatusEnum;
import com.payment.dto.InitiatePaymentDTO;
import com.payment.dto.PaymentResDTO;
import com.payment.dto.TransactionDTO;
import com.payment.exception.ProcessingException;
import com.payment.http.HttpServiceEngine;
import com.payment.repository.TransactionDao;
import com.payment.service.PaymentStatusService;
import com.payment.service.impl.PaymentServiceImpl;
import com.payment.stripeprovider.PaymentRes;
import com.payment.util.GsonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
	
	
	@Mock
	private PaymentStatusService paymentStatusService;
	@Mock
	private HttpServiceEngine httpServiceEngine;
	@Mock
	private Gson gson;
	
	@Mock
	private TransactionDao transactionDao;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private GsonUtils gsonUtils;
	
	@InjectMocks
	private PaymentServiceImpl paymentServiceImpl;
	
	@Test
	public void testCreatePayment() {
		log.info("This is the test of creating a payment : {}", paymentServiceImpl);
		
		//Arrange
		TransactionDTO txnDTO=new TransactionDTO();
		
		
		//Act
		paymentServiceImpl.CreatePayment(txnDTO);
		
		
		//Assert
		assertEquals(TransactionStatusEnum.CREATED.getName(),txnDTO.getTxnStatus());
		assertNotNull(txnDTO.getTxnReference());
		
	}
	
	
	@Test
	public void testInitiatePaymentNoTxnExist() {
		log.info("This is the test of initiate a payment for exception : {}", paymentServiceImpl);
		
		//Arrange		
		String txnReference="txnRef-Ref1";
		InitiatePaymentDTO reqDTO=new InitiatePaymentDTO();
		
		
		//Act
		ProcessingException processingExp = assertThrows(ProcessingException.class,
				()->paymentServiceImpl.InitiatePayment(txnReference, reqDTO));
		
		
		
		//Assert
		assertNotNull(processingExp);
		assertEquals(HttpStatus.BAD_REQUEST, processingExp.getHttpStatus());
		assertEquals(ErrorCodeEnum.INVALID_TXN_REFERENCE.getErrorCode(), processingExp.getErrorCode());
		assertEquals(ErrorCodeEnum.INVALID_TXN_REFERENCE.getErrorMessage(), processingExp.getErrorMessage());
	}
	
	@Test
	public void testInitiatePaymentSuccess() {
		log.info("This is the test of initiate a payment for success : {}", paymentServiceImpl);
		
		//Arrange		
		String txnReference="8f327e01-8c0b-45d7-a815-5c7f9c7ad83d";
		InitiatePaymentDTO reqDTO=new InitiatePaymentDTO();
		
		TransactionDTO txnDTO=new TransactionDTO();
		when(transactionDao.getTransactionByReference(txnReference)).thenReturn(txnDTO);
		
		ResponseEntity<String> httpResponse=new ResponseEntity<>("Resource created successfully", HttpStatus.CREATED);;
		when(httpServiceEngine.makeHttpCall(any())).thenReturn(httpResponse);
		
		PaymentRes paymentReq = new PaymentRes();
		paymentReq.setUrl("http://localhsotpayment.com");
		when(gsonUtils.fromJson(anyString(), eq(PaymentRes.class))).thenReturn(paymentReq);
		
		PaymentResDTO paymentResDTO = new PaymentResDTO();
		paymentResDTO.setId("paymentResID213123");
		paymentResDTO.setUrl("http://localhost.com");
		when(modelMapper.map(paymentReq, PaymentResDTO.class)).thenReturn(paymentResDTO);
		
		
		//Act
		TransactionDTO initiatePayment = paymentServiceImpl.InitiatePayment(txnReference, reqDTO);
	
		
		
		//Assert
		assertEquals(TransactionStatusEnum.PENDING.getName(),initiatePayment.getTxnStatus());
		
	}
}
