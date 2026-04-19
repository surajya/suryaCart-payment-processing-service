package com.payment.entity.activemq;

import java.io.Serializable;

import lombok.Data;

@Data
public class StatusMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String txnReference;
	private String txnStatus;


}

