package com.payment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "transactions_history")
public class TransactionsHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public String txnReference;

	public String txnStatus;

	public String paymentMethod;

	public String paymentType;

	public String paymentProvider;

	public LocalDateTime createdAt;
}
