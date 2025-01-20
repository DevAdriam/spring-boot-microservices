package com.example.ecommerce.order_service.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

        private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);

        @RabbitListener(queues = "new-orders")
        void listenNewOrdersQueue(String message){
                LOGGER.info("Message received -> {}" , message);
        }

}
