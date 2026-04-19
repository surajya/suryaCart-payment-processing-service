package com.payment.service;

import com.payment.dto.TransactionDTO;

public interface PaymentStatusService {
	
	public TransactionDTO processStatus(TransactionDTO transactionDTO);
}
