package com.hulkhiretech.payment.dto;

import java.util.List;

import com.hulkhiretech.payment.pojo.Item;

import lombok.Data;

@Data
public class InitiatePaymentDTO {
	
	private String successUrl;
	private String cancelUrl;
	
	private List<Item> lineItems;

}
