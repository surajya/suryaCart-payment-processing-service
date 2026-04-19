package com.payment.service;

import com.payment.dto.TransactionDTO;

public interface TransactionStatusHandler {

	public TransactionDTO processStatus(TransactionDTO transactionDTO);

}
