package com.payment.util;

import org.modelmapper.AbstractConverter;

import com.payment.constant.TransactionStatusEnum;

public class TransactionStatusEnumConverter extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		// TODO Auto-generated method stub
		return TransactionStatusEnum.getByName(source).getId();
	}

}
