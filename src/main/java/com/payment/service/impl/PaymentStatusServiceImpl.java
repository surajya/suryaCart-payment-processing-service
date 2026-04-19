package com.payment.service.impl;

import org.springframework.stereotype.Service;

import com.payment.constant.TransactionStatusEnum;
import com.payment.dto.TransactionDTO;
import com.payment.service.PaymentStatusFactory;
import com.payment.service.PaymentStatusService;
import com.payment.service.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentStatusServiceImpl implements PaymentStatusService {
	
	private final PaymentStatusFactory paymentStatusFactory;
	
	@Override
	public TransactionDTO processStatus(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		
		log.info("recieved transactionDTO: {} || paymentStatusFactory: {} ", transactionDTO, paymentStatusFactory);
		
		TransactionStatusEnum statusEnum = TransactionStatusEnum.getByName(transactionDTO.getTxnStatus());
		
		
		TransactionStatusHandler statusHandler = paymentStatusFactory.getHandler(statusEnum);
		if(statusEnum==null) {
			log.info("no status is found null for transaction"+statusEnum);
			
			
			
			if(statusHandler==null) {
				//throw custom exception
			}
		}
		
		TransactionDTO processStatus = statusHandler.processStatus(transactionDTO);
		log.info("transactionDTO : "+transactionDTO);
		return transactionDTO;
	}



}
