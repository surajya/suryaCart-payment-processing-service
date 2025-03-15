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
public class SuccessStatusHandler implements TransactionStatusHandler {
	private final TransactionDao transactionDao;
	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		//invoke DAO layer
		
		String txnStatus = transactionDTO.getTxnStatus();
		log.info("Processing SUCCESS status||txnDto:" + transactionDTO);

		transactionDao.updateTransactionStatusDetails(transactionDTO);

		log.info("Updated Txn in DB||txnDto:" + transactionDTO);
		
		return transactionDTO;
	}

}
