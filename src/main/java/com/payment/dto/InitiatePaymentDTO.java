package com.payment.dto;

import java.util.List;

import com.payment.pojo.Item;

import lombok.Data;

@Data
public class InitiatePaymentDTO {

	private String successUrl;

	private String cancelUrl;

	private List<Item> lineItems;

}
