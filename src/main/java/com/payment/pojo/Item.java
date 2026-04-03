package com.payment.pojo;

import lombok.Data;

@Data
public class Item {

	private int quantity;

	private String currency;

	private String productName;

	private int unitAmount;

}
