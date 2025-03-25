package com.hulkhiretech.payment.service.impl.activemq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.hulkhiretech.payment.pojo.activemq.StatusMessage;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StatusMessageProducer {
    private static final String QUEUE_NAME = "status.queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(StatusMessage message) {
        log.info("Sending message: " + message);
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
    }
}


