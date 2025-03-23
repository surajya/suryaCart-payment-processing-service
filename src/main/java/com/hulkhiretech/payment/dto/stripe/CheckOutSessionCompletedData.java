package com.hulkhiretech.payment.dto.stripe;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data

public class CheckOutSessionCompletedData {
	private String id;
	private String status;
	
	@SerializedName("payment_status")
	private String paymentStatus;
}
