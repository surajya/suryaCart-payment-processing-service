package com.payment.service.impl.statusHandler;

import org.springframework.stereotype.Service;

import com.payment.dto.TransactionDTO;
import com.payment.service.TransactionService;
import com.payment.service.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateStatusHandler implements TransactionStatusHandler {

	private final TransactionService transactionService;

	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		//invoke DAO layer
		TransactionDTO transaction = transactionService.createTransaction(transactionDTO);
		log.info("txnDTO created successfully in DB WITH transaction: " + transaction);
		return transaction;
	}

}
