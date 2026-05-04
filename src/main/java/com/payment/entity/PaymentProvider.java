package com.payment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "payment_provider")
public class PaymentProvider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "payment_provider_name")
	public String paymentProviderName;

	public int status;

	@Column(name = "creation_date")
	public LocalDateTime createdAt;

}
