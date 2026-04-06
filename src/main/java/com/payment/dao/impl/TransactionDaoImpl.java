package com.payment.dao.impl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.payment.dao.TransactionDao;
import com.payment.dto.TransactionDTO;
import com.payment.entity.TransactionsEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDao {

	private final ModelMapper mapper;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public TransactionDTO createTransaction(TransactionDTO txnDTO) {
		// TODO Auto-generated method stub
		log.info("Creating Transaction in DB||txnDto:" + txnDTO);

		TransactionsEntity txnEntity = mapper.map(txnDTO, TransactionsEntity.class);
		log.info("Convert transaction dto into transaction ENTITY :" + txnEntity);

		String sql = "INSERT INTO payments.transactions " +
				"(userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, amount, currency, merchantTxnReference, txnReference,"
				+
				" providerReference, errorCode, errorMessage, retryCount) " +
				"VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, :merchantTxnReference,"
				+
				" :txnReference, :providerReference, :errorCode, :errorMessage, :retryCount)";

		SqlParameterSource params = new BeanPropertySqlParameterSource(txnEntity);

		log.info("txnDTO createTransaction in database update value: " + params);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int updateCount = jdbcTemplate.update(sql, params, keyHolder, new String[] {"id"});
		if (updateCount == 0) {
			throw new RuntimeException("Insert failed, no rows affected.");
		}
		int id = keyHolder.getKey().intValue();
		txnDTO.setId(id);
		log.info("ID ADD after of txnDTO: " + txnDTO);
		return txnDTO;
	}

	@Override
	public TransactionDTO getTransactionByReference(String txnReference) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM payments.transactions "
				+ "WHERE txnReference = :txnReference";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("txnReference", txnReference);

		try {

			TransactionsEntity transactionEntity = jdbcTemplate.queryForObject(sql, params,
					new BeanPropertyRowMapper<>(TransactionsEntity.class));

			TransactionDTO txnDTO = mapper.map(transactionEntity, TransactionDTO.class);

			log.info("Transaction fetched from database || txnDTO: " + txnDTO);

			return txnDTO;

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while fetching transaction from database || txnReference: " + txnReference, e);
			return null;
		}
	}

	@Override
	public TransactionDTO updateTransactionStatusDetails(TransactionDTO txnDto) {
		String sql = "UPDATE payments.transactions "
				+ "SET txnStatusId = :txnStatusId, providerReference = :providerReference, "
				+ "errorCode = :errorCode, errorMessage = :errorMessage "
				+ "WHERE txnReference = :txnReference";

		TransactionsEntity transaction = mapper.map(txnDto, TransactionsEntity.class);
		SqlParameterSource params = new BeanPropertySqlParameterSource(transaction);

		jdbcTemplate.update(sql, params);

		log.info("Transaction status updated in DB||txnDto:{}", txnDto);

		return txnDto;
	}

	@Override
	public TransactionDTO getTransactionByProviderReference(String providerReference) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM payments.transactions "
				+ "WHERE providerReference = :providerReference";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("providerReference", providerReference);

		try {

			TransactionsEntity transactionEntity = jdbcTemplate.queryForObject(sql, params,
					new BeanPropertyRowMapper<>(TransactionsEntity.class));

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
