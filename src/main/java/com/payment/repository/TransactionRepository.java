package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.entity.TransactionsEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Integer> {

	public abstract TransactionsEntity findBytxnReference(String txnReference);

	public abstract TransactionsEntity findByproviderReference(String providerReference);

}
