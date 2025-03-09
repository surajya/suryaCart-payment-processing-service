package com.hulkhiretech.payment.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionsEntity {
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
