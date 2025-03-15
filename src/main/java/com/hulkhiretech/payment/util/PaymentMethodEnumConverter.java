package com.hulkhiretech.payment.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payment.constant.PaymentMethodEnum;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		// TODO Auto-generated method stub
		return PaymentMethodEnum.getByName(source).getId();
	}

}
