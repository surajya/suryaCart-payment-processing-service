package com.hulkhiretech.payment.service.interfaces;

import com.hulkhiretech.payment.dto.TransactionDTO;

public interface PaymentStatusService {
	
	public TransactionDTO processStatus(TransactionDTO transactionDTO);
}
