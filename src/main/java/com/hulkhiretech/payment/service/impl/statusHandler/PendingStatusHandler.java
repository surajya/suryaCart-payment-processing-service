package com.hulkhiretech.payment.service.impl.statusHandler;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PendingStatusHandler implements TransactionStatusHandler {

	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		//invoke DAO layer
		return null;
	}

}
