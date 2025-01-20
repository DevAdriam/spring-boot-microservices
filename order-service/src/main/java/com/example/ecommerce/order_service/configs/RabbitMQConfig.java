package com.example.ecommerce.order_service.configs;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import  org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

import com.example.ecommerce.order_service.ApplicationProperties;

@Configuration
public class RabbitMQConfig {

       private ApplicationProperties properties;

       public RabbitMQConfig(ApplicationProperties properties){
        this.properties = properties;
       }

       @Bean
       DirectExchange orderExchange(){
        return new DirectExchange(properties.orderEventsExchange());
       }

       @Bean
       Queue newOrdersQueue(){
        return new Queue(properties.newOrdersQueue());
       }

       @Bean
       Binding newOrdersQueueBindign(){
        return BindingBuilder.bind(newOrdersQueue()).to(orderExchange()).with(properties.newOrdersQueue());
       }
        
}
