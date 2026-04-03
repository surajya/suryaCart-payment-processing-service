package com.payment.service.impl.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.payment.pojo.activemq.StatusMessage;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StatusMessageConsumer {
    
    @JmsListener(destination = "status.queue")
    public void receiveMessage(StatusMessage message) {
        log.info("Received message After DB updated :  " + message);
    }
}


