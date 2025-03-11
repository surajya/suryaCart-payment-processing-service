package com.hulkhiretech.payment.service.interfaces;

import com.hulkhiretech.payment.dto.TransactionDTO;

public interface PaymentService {
	public String CreatePayment(TransactionDTO transactionDTO);
	
	public String InitiatePayment();
}
