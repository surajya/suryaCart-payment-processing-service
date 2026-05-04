package com.payment.service.impl.statusHandler;

import org.modelmapper.ModelMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.payment.dto.TransactionDTO;
import com.payment.entity.activemq.StatusMessage;
import com.payment.service.TransactionStatusHandler;
import com.payment.service.impl.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class SuccessStatusHandler implements TransactionStatusHandler {

	private final TransactionService transactionService;
	private static final String QUEUE_NAME = "status.queue";


	private final JmsTemplate jmsTemplate;
	private final ModelMapper modelMapper;


	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		//invoke DAO layer

		String txnStatus = transactionDTO.getTxnStatus();
		log.info("Processing SUCCESS status||txnDto:" + transactionDTO);

		transactionService.updateTransactionStatusDetails(transactionDTO);

		log.info("Updated Txn in DB||txnDto:" + transactionDTO);

		//invoke activeMQ after successful transaction

		StatusMessage message = modelMapper.map(transactionDTO, StatusMessage.class);

		jmsTemplate.convertAndSend(QUEUE_NAME, message);

		return transactionDTO;
	}

}
