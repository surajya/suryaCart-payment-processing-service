package com.payment.service.interfaces;

import com.payment.dto.TransactionDTO;

public interface PaymentStatusService {
	
	public TransactionDTO processStatus(TransactionDTO transactionDTO);
}
