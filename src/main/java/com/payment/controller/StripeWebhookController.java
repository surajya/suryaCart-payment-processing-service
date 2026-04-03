package com.payment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.payment.dto.stripe.StripeEventDTO;
import com.payment.pojo.stripe.StripeEvent;
import com.payment.service.interfaces.StripeWebhookService;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stripe/webhook")
@Slf4j
@RequiredArgsConstructor
public class StripeWebhookController {
	
	@Value("${stripe.endPointSecret}")
	private String endPointSecret;
	
	private final Gson gson;
	private final StripeWebhookService stripeWebhookService;
	private final ModelMapper mapper;
	
	@PostMapping
	public ResponseEntity<String> processStripeEvent(@RequestBody String event, @RequestHeader ("Stripe-Signature") String signatureHeader) {
		log.info("message of events: " + event);
		log.info("signature header: " + signatureHeader);
		
		
		try {
			Webhook.constructEvent(event, signatureHeader, endPointSecret);
			log.info("Successfully verified webhook signauture");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("invalid webhook signature"+e);
			return ResponseEntity.badRequest().build();
		}
		
		StripeEvent stripeEvent = gson.fromJson(event, StripeEvent.class);
		log.info("recieved StripeEvent: " + stripeEvent);
		StripeEventDTO stripeEventDTO= mapper.map(stripeEvent,StripeEventDTO.class);
		log.info("StripeEvent connvert seccessfully into StripeEventDTO: " + stripeEventDTO);
		stripeWebhookService.processEvent(stripeEventDTO);
		log.info("event process successful");
		
		return ResponseEntity.ok().build();
	}
}
