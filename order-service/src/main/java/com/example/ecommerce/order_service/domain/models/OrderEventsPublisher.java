package com.example.ecommerce.order_service.domain.models;

import org.springframework.stereotype.Component;

import com.example.ecommerce.order_service.ApplicationProperties;
import com.example.ecommerce.order_service.core.configs.RabbitMQProducer;
import com.example.ecommerce.order_service.domain.dto.OrderCreatedEvent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class OrderEventsPublisher {

        private final ApplicationProperties properties;
        private final RabbitMQProducer producer;

        public void publishOrderEvent(OrderCreatedEvent orderEvent){
                log.info("Sending message...");
                producer.sendMessage(properties.newOrdersQueue(),orderEvent);
        }
        
}
