package com.hulkhiretech.payment.dto.stripe;

import lombok.Data;

@Data
public class StripeEventDTO {
	private String id;
	private String type;
	private StripeDataDTO data;
}
