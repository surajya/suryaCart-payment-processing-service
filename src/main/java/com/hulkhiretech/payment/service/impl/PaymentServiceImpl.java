package com.hulkhiretech.payment.service.impl;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.dto.TransactionsDTO;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{

	@Override
	public String CreatePayment(TransactionsDTO transactionDTO) {
		// TODO Auto-generated method stub
		log.info("PaymentServiceImpl.CreatePayment");
		return "return from PaymentServiceImpl.CreatePayment";
	}

	@Override
	public String InitiatePayment() {
		// TODO Auto-generated method stub
		return null;
	}

}
