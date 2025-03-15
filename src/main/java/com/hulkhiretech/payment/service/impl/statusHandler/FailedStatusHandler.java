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
public class FailedStatusHandler implements TransactionStatusHandler {

	private final TransactionDao transactionDao;

	@Override
	public TransactionDTO processStatus(TransactionDTO txnDto) {
		// TODO Auto-generated method stub
		//invoke DAO layer

		log.info("Processing FAILED status||txnDto:" + txnDto);

		transactionDao.updateTransactionStatusDetails(txnDto);

		log.info("Updated Txn in DB||txnDto:" + txnDto);

		return txnDto;
	}

}
