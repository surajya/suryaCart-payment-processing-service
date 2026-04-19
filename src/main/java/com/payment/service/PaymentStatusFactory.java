package com.payment.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.payment.constant.TransactionStatusEnum;
import com.payment.service.impl.statusHandler.CreateStatusHandler;
import com.payment.service.impl.statusHandler.FailedStatusHandler;
import com.payment.service.impl.statusHandler.InitiatedStatusHandler;
import com.payment.service.impl.statusHandler.PendingStatusHandler;
import com.payment.service.impl.statusHandler.SuccessStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentStatusFactory {

	private ApplicationContext context;
	
	PaymentStatusFactory(ApplicationContext context) {
		this.context =context;
	}
	
	public TransactionStatusHandler getHandler(TransactionStatusEnum statusEnum) {
		
		switch (statusEnum) {
		
		case CREATED:
			return context.getBean(CreateStatusHandler.class);
			
			
		case INITIATED:
			return context.getBean(InitiatedStatusHandler.class);
		case PENDING:
			
			return context.getBean(PendingStatusHandler.class);
		case SUCCESS:
			return context.getBean(SuccessStatusHandler.class);
		case FAILED:
			return context.getBean(FailedStatusHandler.class);
		
		}
		
		log.info("statusEnum is not handled || statusEnum:{}", statusEnum);
		return null;
	}
}
