package com.payment.service.impl.statusHandler;

import org.springframework.stereotype.Service;

import com.payment.dto.TransactionDTO;
import com.payment.repository.TransactionRepository;
import com.payment.service.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class PendingStatusHandler implements TransactionStatusHandler {

	private final TransactionRepository transactionRepository;

	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		log.info("Processing PENDING status||txnDto:" + transactionDTO);

		transactionRepository.updateTransactionStatusDetails(transactionDTO);

		log.info("Updated Txn in DB||txnDto:" + transactionDTO);
		return transactionDTO;
	}

}
