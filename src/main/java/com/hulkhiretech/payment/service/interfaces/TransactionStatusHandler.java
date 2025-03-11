package com.hulkhiretech.payment.service.interfaces;

import com.hulkhiretech.payment.dto.TransactionDTO;

public interface TransactionStatusHandler {
	public TransactionDTO processStatus(TransactionDTO transactionDTO);
}
