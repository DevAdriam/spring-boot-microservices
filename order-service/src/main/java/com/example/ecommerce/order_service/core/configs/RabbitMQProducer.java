package com.example.ecommerce.order_service.core.configs;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.ecommerce.order_service.ApplicationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;
    private final ObjectMapper objectMapper;
    
    public void sendMessage(String routingKey, Object payload) {
        log.info("send message with routing key {}",routingKey);
       Object JSONObject = ConvertJSONString(payload);
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, JSONObject);
    }

    public Object ConvertJSONString(Object payload){
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
