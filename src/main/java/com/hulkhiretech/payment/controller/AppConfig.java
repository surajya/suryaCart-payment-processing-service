package com.hulkhiretech.payment.controller;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.entity.TransactionsEntity;
import com.hulkhiretech.payment.util.PaymentMethodEnumConverter;
import com.hulkhiretech.payment.util.PaymentTypeEnumConverter;
import com.hulkhiretech.payment.util.ProviderEnumConverter;
import com.hulkhiretech.payment.util.TransactionStatusEnumConverter;
import com.hulkhiretech.payment.util.mmconverts.idtostring.PaymentMethodEnumIdToStringConverter;
import com.hulkhiretech.payment.util.mmconverts.idtostring.PaymentTypeEnumIdToStringConverter;
import com.hulkhiretech.payment.util.mmconverts.idtostring.ProviderEnumIdToStringConverter;
import com.hulkhiretech.payment.util.mmconverts.idtostring.TransactionStatusEnumIdToStringConverter;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper mapper() {

		ModelMapper modelMapper = new ModelMapper();

		// Set the matching strategy to STRICT
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Converter<String, Integer> paymentMethodEnumConverter = new PaymentMethodEnumConverter();
		Converter<String, Integer> paymentTypeEnumConverter = new PaymentTypeEnumConverter();
		Converter<String, Integer> providerEnumConverter = new ProviderEnumConverter();
		Converter<String, Integer> transactionStatusEnumConverter = new TransactionStatusEnumConverter();
		
		modelMapper.addMappings(new PropertyMap<TransactionDTO, TransactionsEntity>() {

			@Override
			protected void configure() {
				// TODO Auto-generated method stub
				using(paymentMethodEnumConverter).map(source.getPaymentMethod(),destination.getPaymentMethodId());
				using(paymentTypeEnumConverter).map(source.getPaymentType(),destination.getPaymentTypeId());
				using(providerEnumConverter).map(source.getProvider(),destination.getProviderId());
				using(transactionStatusEnumConverter).map(source.getTxnStatus(),destination.getTxnStatusId());
			}
		});
		
		//this model mapper to use for convert transactionEntity to TransactionDTO
		modelMapper.addMappings(new PropertyMap<TransactionsEntity, TransactionDTO>() {

			@Override
			protected void configure() {
				// TODO Auto-generated method stub
				using(new PaymentMethodEnumIdToStringConverter()).map(source.getPaymentMethodId(),destination.getPaymentMethod());
				using(new PaymentTypeEnumIdToStringConverter()).map(source.getPaymentTypeId(),destination.getPaymentType());
				using(new ProviderEnumIdToStringConverter()).map(source.getProviderId(),destination.getProvider());
				using(new TransactionStatusEnumIdToStringConverter()).map(source.getTxnStatusId(),destination.getTxnStatus());
			}
		});
		
		return modelMapper;
	}

}
