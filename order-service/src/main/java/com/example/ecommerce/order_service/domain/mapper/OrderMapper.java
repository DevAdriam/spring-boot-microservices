package com.example.ecommerce.order_service.domain.mapper;

import org.springframework.stereotype.Component;

import com.example.ecommerce.order_service.domain.dto.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.dto.CreateOrderResponse;
import com.example.ecommerce.order_service.domain.dto.OrderCreatedEvent;
import com.example.ecommerce.order_service.domain.models.OrderEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderMapper {

        private final ObjectMapper objectMapper ;
        
        public  OrderEntity toOrderEntity (CreateOrderRequest orderRequest) {
                try {
                        return  objectMapper.convertValue(orderRequest, OrderEntity.class);
                } catch (Exception e) {
                       throw new RuntimeException(e.getMessage());
                }
        }

        public CreateOrderResponse toOrderResponse(OrderEntity orderEntity){
                try {
                        System.out.println(orderEntity);
                        return objectMapper.convertValue(orderEntity, CreateOrderResponse.class);
                } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                }

        }

        public  OrderCreatedEvent toOrderCreatedEvent(OrderEntity orderEntity ){
                try {
                        return objectMapper.convertValue(orderEntity, OrderCreatedEvent.class);
                } catch (Exception e) {
                       throw new RuntimeException(e.getMessage());
                }
        }
        

}