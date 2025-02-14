package com.example.ecommerce.order_service.domain;


import com.example.ecommerce.order_service.domain.models.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderMapper {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        static OrderEntity toOrderEntity (CreateOrderRequest orderRequest) {
                try {
                        return objectMapper.convertValue(orderRequest, OrderEntity.class);
                } catch (Exception e) {
                       throw new RuntimeException(e.getMessage());
                }
        }

}