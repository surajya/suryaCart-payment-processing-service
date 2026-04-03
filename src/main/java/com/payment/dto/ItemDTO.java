package com.payment.dto;

import lombok.Data;

@Data
public class ItemDTO {

	private int quantity;

	private String currency;

	private String productName;

	private int unitAmount;

}
