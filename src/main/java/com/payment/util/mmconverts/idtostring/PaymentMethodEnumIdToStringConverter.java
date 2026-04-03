package com.payment.util.mmconverts.idtostring;

import org.modelmapper.AbstractConverter;

import com.payment.constant.PaymentMethodEnum;

public class PaymentMethodEnumIdToStringConverter extends AbstractConverter<Integer,String>{

	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return PaymentMethodEnum.getById(source).getName();
	}

}
