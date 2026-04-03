package com.payment.util;

import org.modelmapper.AbstractConverter;

import com.payment.constant.ProviderEnum;

public class ProviderEnumConverter extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		// TODO Auto-generated method stub
		return ProviderEnum.getByName(source).getId();
	}

}
