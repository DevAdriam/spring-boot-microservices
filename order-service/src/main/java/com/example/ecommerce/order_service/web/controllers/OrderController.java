package com.example.ecommerce.order_service.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.order_service.configs.RabbitMQProducer;

@RestController
public class OrderController {

        private final RabbitMQProducer rabbitMQProducer;

        public OrderController(RabbitMQProducer rabbitMQProducer){
                this.rabbitMQProducer = rabbitMQProducer;
        }

        @GetMapping("/test")
        public ResponseEntity<String> testMessage(@RequestParam("orderId") String orderId){
                rabbitMQProducer.sendMessage("new-orders", orderId);
                return ResponseEntity.ok("Message sent successfully!!");
        }
        
}
