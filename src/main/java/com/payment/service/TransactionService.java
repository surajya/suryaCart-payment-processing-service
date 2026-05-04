package com.payment.service;

import com.payment.dto.TransactionDTO;

public interface TransactionService {

	public abstract TransactionDTO createTransaction(TransactionDTO txnDTO);

	public abstract TransactionDTO getTransactionByReference(String txnReference);

	public abstract TransactionDTO updateTransactionStatusDetails(TransactionDTO txnDto);

	public abstract TransactionDTO getTransactionByProviderReference(String providerReference);
}
