package com.payment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.entity.activemq.StatusMessage;
import com.payment.service.impl.activemq.StatusMessageProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/send-status")
@Slf4j
@RequiredArgsConstructor
public class StatusController {

    
    private final StatusMessageProducer producer;

    @PostMapping
    public String sendStatus(@RequestBody StatusMessage message) {
    	log.info("Requesting status message from client");
        producer.sendMessage(message);
        return "Message sent to the queue successfully!";
    }
}
