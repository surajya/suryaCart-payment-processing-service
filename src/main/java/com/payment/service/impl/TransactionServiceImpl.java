package com.payment.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.payment.dto.TransactionDTO;
import com.payment.entity.TransactionsEntity;
import com.payment.repository.TransactionRepository;
import com.payment.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final ModelMapper mapper;


	private final TransactionRepository transactionRepository;

	@Override
	public TransactionDTO createTransaction(TransactionDTO txnDTO) {
		log.info("Creating Transaction in DB||txnDto:" + txnDTO);
		TransactionsEntity txnEntity = mapper.map(txnDTO, TransactionsEntity.class);
		log.info("Convert transaction dto into transaction ENTITY :" + txnEntity);
		transactionRepository.save(txnEntity);
		log.info("ID ADD after of txnDTO: " + txnDTO);
		return txnDTO;
	}

	@Override
	public TransactionDTO getTransactionByReference(String txnReference) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("txnReference", txnReference);
		try {
			TransactionsEntity transactionEntity = transactionRepository.findBytxnReference(txnReference);
			TransactionDTO txnDTO = mapper.map(transactionEntity, TransactionDTO.class);
			log.info("Transaction fetched from database || txnDTO: " + txnDTO);
			return txnDTO;

		} catch (Exception e) {
			log.error("Error while fetching transaction from database || txnReference: " + txnReference, e);
			return null;
		}
	}

	@Override
	public TransactionDTO updateTransactionStatusDetails(TransactionDTO txnDto) {
		TransactionsEntity txnEntity = mapper.map(txnDto, TransactionsEntity.class);
		transactionRepository.save(txnEntity);
		log.info("Transaction status updated in DB||txnDto:{}", txnDto);

		return txnDto;
	}

	@Override
	public TransactionDTO getTransactionByProviderReference(String providerReference) {
		try {

			TransactionsEntity transactionEntity = transactionRepository.findByproviderReference(providerReference);
			TransactionDTO txnDTO = mapper.map(transactionEntity, TransactionDTO.class);

			log.info("Transaction fetched from database || txnDTO: " + txnDTO);

			return txnDTO;

		} catch (Exception e) {
			log.error("Error while fetching transaction from database || txnReference: " + providerReference, e);
			return null;
		}
	}

}
