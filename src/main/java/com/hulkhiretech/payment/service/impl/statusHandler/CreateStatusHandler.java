package com.hulkhiretech.payment.service.impl.statusHandler;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.dao.interfaces.TransactionDao;
import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.service.interfaces.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateStatusHandler implements TransactionStatusHandler {
	
	private final TransactionDao txnDao;

	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		//invoke DAO layer
		TransactionDTO transaction = txnDao.createTransaction(transactionDTO);
		log.info("txnDTO created successfully in DB WITH transaction: " + transaction);
		return transaction;
	}

}
