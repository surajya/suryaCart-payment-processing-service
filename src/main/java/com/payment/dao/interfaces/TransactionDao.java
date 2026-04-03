package com.payment.dao.interfaces;

import com.payment.dto.TransactionDTO;

public interface TransactionDao {
	
	public abstract TransactionDTO createTransaction(TransactionDTO txnDTO);
	
	public abstract TransactionDTO getTransactionByReference(String txnReference);

	public abstract TransactionDTO updateTransactionStatusDetails(TransactionDTO txnDto);

	public abstract TransactionDTO getTransactionByProviderReference(String providerReference);


}
