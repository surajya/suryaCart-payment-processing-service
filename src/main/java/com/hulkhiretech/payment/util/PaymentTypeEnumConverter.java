package com.hulkhiretech.payment.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payment.constant.PaymentTypeEnum;

public class PaymentTypeEnumConverter extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		// TODO Auto-generated method stub
		return PaymentTypeEnum.getByName(source).getId();
	}

}
