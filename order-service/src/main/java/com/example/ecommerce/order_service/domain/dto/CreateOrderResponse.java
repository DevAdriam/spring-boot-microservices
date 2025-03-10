package com.example.ecommerce.order_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateOrderResponse(
        String orderNo
        
) {

}
