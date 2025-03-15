package com.hulkhiretech.payment.service.interfaces;

import com.hulkhiretech.payment.dto.InitiatePaymentDTO;
import com.hulkhiretech.payment.dto.TransactionDTO;

public interface PaymentService {
	public TransactionDTO CreatePayment(TransactionDTO transactionDTO);
	
	public TransactionDTO InitiatePayment(String txnReference,InitiatePaymentDTO reqDto);
}
