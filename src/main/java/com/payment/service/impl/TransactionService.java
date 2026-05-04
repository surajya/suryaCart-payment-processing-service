package com.payment.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.payment.dto.TransactionDTO;
import com.payment.entity.TransactionsEntity;
import com.payment.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

	private final ModelMapper mapper;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final TransactionRepository transactionRepository;


	public TransactionDTO createTransaction(TransactionDTO txnDTO) {
		// TODO Auto-generated method stub
		log.info("Creating Transaction in DB||txnDto:" + txnDTO);

		TransactionsEntity txnEntity = mapper.map(txnDTO, TransactionsEntity.class);
		log.info("Convert transaction dto into transaction ENTITY :" + txnEntity);
		transactionRepository.save(txnEntity);


		//		String sql = "INSERT INTO payments.transactions " +
		//				"(user_id, payment_method_id, provider_id, payment_type_id, txn_status_id, amount, currency, merchant_txn_reference, txn_reference, "
		//				+
		//				"provider_reference, error_code, error_message, retry_count) " +
		//				"VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, :merchantTxnReference, "
		//				+
		//				":txnReference, :providerReference, :errorCode, :errorMessage, :retryCount)";
		//
		//		SqlParameterSource params = new BeanPropertySqlParameterSource(txnEntity);
		//
		//		log.info("txnDTO createTransaction in database update value: " + params);
		//
		//		KeyHolder keyHolder = new GeneratedKeyHolder();
		//		int updateCount = jdbcTemplate.update(sql, params, keyHolder, new String[] {"id"});
		//		if (updateCount == 0) {
		//			throw new RuntimeException("Insert failed, no rows affected.");
		//		}
		//		int id = keyHolder.getKey().intValue();
		//		txnDTO.setId(id);
		log.info("ID ADD after of txnDTO: " + txnDTO);
		return txnDTO;
	}


	public TransactionDTO getTransactionByReference(String txnReference) {
		// TODO Auto-generated method stub
		//		String sql = "SELECT * FROM payments.transactions "
		//				+ "WHERE txn_Reference = :txnReference";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("txnReference", txnReference);

		try {

			//			TransactionsEntity transactionEntity = jdbcTemplate.queryForObject(sql, params,
			//					new BeanPropertyRowMapper<>(TransactionsEntity.class));

			TransactionsEntity transactionEntity = transactionRepository.findBytxnReference(txnReference);
			TransactionDTO txnDTO = mapper.map(transactionEntity, TransactionDTO.class);

			log.info("Transaction fetched from database || txnDTO: " + txnDTO);

			return txnDTO;

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while fetching transaction from database || txnReference: " + txnReference, e);
			return null;
		}
	}


	public TransactionDTO updateTransactionStatusDetails(TransactionDTO txnDto) {
		//		String sql = "UPDATE payments.transactions " +
		//				"SET txn_status_id = :txnStatusId, provider_reference = :providerReference, " +
		//				"error_code = :errorCode, error_message = :errorMessage " +
		//				"WHERE txn_reference = :txnReference";

		TransactionsEntity txnEntity = mapper.map(txnDto, TransactionsEntity.class);
		//		SqlParameterSource params = new BeanPropertySqlParameterSource(transaction);
		//
		//		jdbcTemplate.update(sql, params);

		transactionRepository.save(txnEntity);

		log.info("Transaction status updated in DB||txnDto:{}", txnDto);

		return txnDto;
	}

	public TransactionDTO getTransactionByProviderReference(String providerReference) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM payments.transactions "
				+ "WHERE provider_Reference = :providerReference";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("providerReference", providerReference);

		try {

			//			TransactionsEntity transactionEntity = jdbcTemplate.queryForObject(sql, params,
			//					new BeanPropertyRowMapper<>(TransactionsEntity.class));
			TransactionsEntity transactionEntity = transactionRepository.findByproviderReference(providerReference);
			TransactionDTO txnDTO = mapper.map(transactionEntity, TransactionDTO.class);

			log.info("Transaction fetched from database || txnDTO: " + txnDTO);

			return txnDTO;

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while fetching transaction from database || txnReference: " + providerReference, e);
			return null;
		}
	}

}
