package com.hulkhiretech.payment.service.interfaces;

import com.hulkhiretech.payment.dto.TransactionsDTO;

public interface PaymentService {
	public String CreatePayment(TransactionsDTO transactionDTO);
	
	public String InitiatePayment();
}
