package com.payment.service.interfaces;

import com.payment.dto.TransactionDTO;

public interface TransactionStatusHandler {

	public TransactionDTO processStatus(TransactionDTO transactionDTO);

}
