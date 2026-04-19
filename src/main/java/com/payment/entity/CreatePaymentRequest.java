package com.payment.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreatePaymentRequest {

	private int userId;

	private String paymentMethod;

	private String provider;

	private String paymentType;

	private BigDecimal amount;

	private String currency;

	private String merchantTxnReference;

}
