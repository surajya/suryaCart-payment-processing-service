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
public class FailedStatusHandler implements TransactionStatusHandler {

	private final TransactionService transactionService;

	@Override
	public TransactionDTO processStatus(TransactionDTO txnDto) {
		// TODO Auto-generated method stub
		//invoke DAO layer

		log.info("Processing FAILED status||txnDto:" + txnDto);

		transactionService.updateTransactionStatusDetails(txnDto);

		log.info("Updated Txn in DB||txnDto:" + txnDto);

		return txnDto;
	}

}
