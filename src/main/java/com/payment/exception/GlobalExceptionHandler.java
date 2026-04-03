package com.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payment.constant.ErrorCodeEnum;
import com.payment.pojo.ErrorRes;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ProcessingException.class)
    public ResponseEntity<ErrorRes> handleStripeProviderException(ProcessingException ex) {
		log.error("StripeProviderException occurred:", ex);
		
		ErrorRes errorRes = new ErrorRes();
		errorRes.setErrorCode(ex.getErrorCode());
		errorRes.setErrorMessage(ex.getMessage());
		
        log.info("Returning errorRes:" + errorRes);
        return new ResponseEntity<>(errorRes, ex.getHttpStatus());
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> handleGenericException(Exception ex) {
		log.error("handleGenericException occurred:", ex);
		
		ErrorRes errorRes = new ErrorRes();
		
		errorRes.setErrorCode(ErrorCodeEnum.GENERIC_ERROR.getErrorCode());
		errorRes.setErrorMessage(ErrorCodeEnum.GENERIC_ERROR.getErrorMessage());
		
        log.info("Returning errorRes:" + errorRes);
        return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
