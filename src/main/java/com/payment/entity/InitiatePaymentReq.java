package com.payment.entity;

import java.util.List;

import lombok.Data;

@Data
public class InitiatePaymentReq {

	private String successUrl;

	private String cancelUrl;

	private List<Item> lineItems;

}
