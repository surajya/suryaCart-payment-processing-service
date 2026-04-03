package com.payment.service.interfaces;

import com.payment.dto.InitiatePaymentDTO;
import com.payment.dto.TransactionDTO;

public interface PaymentService {
	public TransactionDTO CreatePayment(TransactionDTO transactionDTO);
	
	public TransactionDTO InitiatePayment(String txnReference,InitiatePaymentDTO reqDto);
}
