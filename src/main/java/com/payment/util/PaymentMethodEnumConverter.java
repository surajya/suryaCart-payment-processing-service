package com.payment.util;

import org.modelmapper.AbstractConverter;

import com.payment.constant.PaymentMethodEnum;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		// TODO Auto-generated method stub
		return PaymentMethodEnum.getByName(source).getId();
	}

}
