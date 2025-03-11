package com.hulkhiretech.payment.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpRequest {
	private HttpMethod method;
	private String url;
	private HttpHeaders headers;
	private Object requestBody;
}
