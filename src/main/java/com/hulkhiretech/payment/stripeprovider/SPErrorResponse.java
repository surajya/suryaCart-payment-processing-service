package com.hulkhiretech.payment.stripeprovider;

import lombok.Data;

@Data
public class SPErrorResponse {
	
	private String errorCode;
	private String errorMessage;

}
