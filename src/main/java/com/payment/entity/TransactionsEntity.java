package com.payment.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class TransactionsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userId;

	private int paymentMethodId;

	private int providerId;

	private int paymentTypeId;

	private int txnStatusId;

	private BigDecimal amount;

	private String currency;

	private String merchantTxnReference;

	private String txnReference;

	private String providerReference;

	private String errorCode;

	private String errorMessage;

	private int retryCount;

}
