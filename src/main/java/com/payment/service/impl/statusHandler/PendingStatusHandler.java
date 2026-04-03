package com.payment.service.impl.statusHandler;

import org.springframework.stereotype.Service;

import com.payment.dao.interfaces.TransactionDao;
import com.payment.dto.TransactionDTO;
import com.payment.service.interfaces.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class PendingStatusHandler implements TransactionStatusHandler {

	private final TransactionDao transactionDao;
	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		log.info("Processing PENDING status||txnDto:" + transactionDTO);

		transactionDao.updateTransactionStatusDetails(transactionDTO);

		log.info("Updated Txn in DB||txnDto:" + transactionDTO);
		return transactionDTO;
	}

}
