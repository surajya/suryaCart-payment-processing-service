package com.hulkhiretech.payment.http;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.hulkhiretech.payment.constant.ErrorCodeEnum;
import com.hulkhiretech.payment.exception.ProcessingException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpServiceEngine {
	
	private RestClient restClient;
	
	public HttpServiceEngine(RestClient.Builder restClientBuilder) {
		this.restClient=restClientBuilder.build();
	}
	
	
	@CircuitBreaker(name = "payment-processing-service001",
			fallbackMethod = "fallbackProcessPayment")

	public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest) {
		
		log.info("Ready to call RestClient : "+restClient);
		ResponseEntity<String> response = null;
		
		try {
			
			response= restClient.method(httpRequest.getMethod())
					.uri(httpRequest.getUrl())
					.headers(headers-> headers.addAll(httpRequest.getHeaders()))
					.body(httpRequest.getRequestBody())
					.retrieve()
					.toEntity(String.class);
			
			return response;
			
		} 
		 catch (HttpClientErrorException | HttpServerErrorException e) {
				// TODO: handle exception
				log.info("Got HttpClientException here :", e);
				
				
				if(e.getStatusCode().equals(HttpStatus.GATEWAY_TIMEOUT) || e.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
					log.info("recieved error from server provider code 5xx type :"+e.getStatusCode(),e);
					//TODO custom error message
					throw new ProcessingException(
	    					ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorCode(), 
	    					ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorMessage(),
	    					HttpStatus.valueOf(e.getStatusCode().value()));
				}
				
				
				//valid error response handling
				log.info("Got getResponseBodyAsString : "+e.getResponseBodyAsString());
				return ResponseEntity.status(e.getStatusCode())
						.body(e.getResponseBodyAsString());
			}
		
		catch (Exception e) {
			// TODO: handle exception
			log.info("Exception occured:"+e);
			//TODO custom error message
			throw new ProcessingException(
					ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorCode(), 
					ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> fallbackProcessPayment(HttpRequest httpRequest, Throwable t) {
		// Handle fallback logic here
		log.error("Fallback method invoked due to exception:" + t.getMessage());
		throw new ProcessingException(
				ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorCode(),
				ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
