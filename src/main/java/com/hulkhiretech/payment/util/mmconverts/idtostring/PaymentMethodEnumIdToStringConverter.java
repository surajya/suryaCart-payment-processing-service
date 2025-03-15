package com.hulkhiretech.payment.util.mmconverts.idtostring;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payment.constant.PaymentMethodEnum;

public class PaymentMethodEnumIdToStringConverter extends AbstractConverter<Integer,String>{

	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return PaymentMethodEnum.getById(source).getName();
	}

}
