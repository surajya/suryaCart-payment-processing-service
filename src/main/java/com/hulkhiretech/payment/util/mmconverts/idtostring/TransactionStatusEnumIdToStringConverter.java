package com.hulkhiretech.payment.util.mmconverts.idtostring;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payment.constant.PaymentTypeEnum;
import com.hulkhiretech.payment.constant.TransactionStatusEnum;

public class TransactionStatusEnumIdToStringConverter extends AbstractConverter<Integer,String>{


	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return TransactionStatusEnum.getById(source).getName();
	}

}
