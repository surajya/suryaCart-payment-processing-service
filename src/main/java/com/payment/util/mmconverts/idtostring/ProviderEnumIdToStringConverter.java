package com.payment.util.mmconverts.idtostring;

import org.modelmapper.AbstractConverter;

import com.payment.constant.PaymentTypeEnum;
import com.payment.constant.ProviderEnum;

public class ProviderEnumIdToStringConverter extends AbstractConverter<Integer,String>{

	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return ProviderEnum.getById(source).getName();
	}

}
