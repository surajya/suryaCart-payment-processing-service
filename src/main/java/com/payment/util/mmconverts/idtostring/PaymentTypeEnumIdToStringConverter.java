package com.payment.util.mmconverts.idtostring;

import org.modelmapper.AbstractConverter;

import com.payment.constant.PaymentMethodEnum;
import com.payment.constant.PaymentTypeEnum;

public class PaymentTypeEnumIdToStringConverter extends AbstractConverter<Integer,String>{

	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return PaymentTypeEnum.getById(source).getName();
	}

}
