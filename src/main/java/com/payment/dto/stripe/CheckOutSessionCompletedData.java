package com.payment.dto.stripe;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CheckOutSessionCompletedData {

	private String id;

	private String status;

	@SerializedName("payment_status")
	private String paymentStatus;
}
