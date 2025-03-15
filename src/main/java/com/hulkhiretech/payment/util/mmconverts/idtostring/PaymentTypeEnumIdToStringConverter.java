package com.hulkhiretech.payment.util.mmconverts.idtostring;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payment.constant.PaymentMethodEnum;
import com.hulkhiretech.payment.constant.PaymentTypeEnum;

public class PaymentTypeEnumIdToStringConverter extends AbstractConverter<Integer,String>{

	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return PaymentTypeEnum.getById(source).getName();
	}

}
