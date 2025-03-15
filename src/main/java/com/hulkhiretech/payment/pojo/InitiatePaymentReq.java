package com.hulkhiretech.payment.pojo;

import java.util.List;

import lombok.Data;

@Data
public class InitiatePaymentReq {
	
	private String successUrl;
	private String cancelUrl;
	
	private List<Item> lineItems;

}
