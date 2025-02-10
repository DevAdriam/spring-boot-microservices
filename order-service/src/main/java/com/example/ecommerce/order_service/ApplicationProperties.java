package com.example.ecommerce.order_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record ApplicationProperties(
        String orderEventsExchange,
        String newOrdersQueue,
        String deliverOrdersQueue,
        String cancelOrdersQueue,
        String errorOrdersQueue) {}
