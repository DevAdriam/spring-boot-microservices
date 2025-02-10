package com.example.ecommerce.order_service.core.configs;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private RabbitTemplate rabbitTemplate;

    @Value("${orders.order-events-exchange}")
    private String orderExchange;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    ;

    public void sendMessage(String routingKey, String message) {
        rabbitTemplate.convertAndSend(orderExchange, routingKey, message);
    }
}
