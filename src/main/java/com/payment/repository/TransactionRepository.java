package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.entity.TransactionsEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Integer> {

	//	public abstract TransactionDTO createTransaction(TransactionDTO txnDTO);
	//
	//	public abstract TransactionDTO getTransactionByReference(String txnReference);
	//
	//	public abstract TransactionDTO updateTransactionStatusDetails(TransactionDTO txnDto);
	//
	//	public abstract TransactionDTO getTransactionByProviderReference(String providerReference);

	public abstract TransactionsEntity findBytxnReference(String txnReference);

	public abstract TransactionsEntity findByproviderReference(String providerReference);

}
