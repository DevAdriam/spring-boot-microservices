package com.example.ecommerce.order_service.domain.dto;
import java.util.Set;

import com.example.ecommerce.order_service.domain.models.OrderEvents;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderCreatedEvent(
        OrderEvents orderEvents,
        String orderNo,
        float total,
        float sub_total,
        Set<OrderItems> orderItems,
        Customer customer,
        String location,
        OrderStatus orderStatus
) {
}
