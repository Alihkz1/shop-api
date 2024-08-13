package com.shop.config.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String routingKey, String message) {
        amqpTemplate.convertAndSend(routingKey, message);
    }
}
