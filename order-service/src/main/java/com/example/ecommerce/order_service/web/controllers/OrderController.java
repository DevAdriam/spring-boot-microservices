package com.example.ecommerce.order_service.web.controllers;

import com.example.ecommerce.order_service.core.configs.RabbitMQProducer;
import com.example.ecommerce.order_service.core.exceptions.OrderNotFoundException;
import com.example.ecommerce.order_service.core.responses.ApiResponse;
import com.example.ecommerce.order_service.core.responses.ResponseMapper;
import com.example.ecommerce.order_service.domain.OrderEntity;
import com.example.ecommerce.order_service.domain.OrderService;
import com.example.ecommerce.order_service.domain.SecurityService;
import com.example.ecommerce.order_service.domain.models.CreateOrderRequest;

import com.example.ecommerce.order_service.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.order_service.domain.base_endpoints.BaseEndpoints;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(BaseEndpoints.ORDER_ENDPOINT)
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;

    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    // @GetMapping("/test")
    // public ResponseEntity<String> testMessage(@RequestParam("orderId") String orderId) {
    //     rabbitMQProducer.sendMessage("new-orders", orderId);
    //     return ResponseEntity.ok("Message sent successfully!!");
    // }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<OrderEntity>> findOrder(@NotNull @RequestParam("id") UUID id ){
       return orderService
               .findOrderById(id)
               .map(order -> ResponseEntity.ok().body(ResponseMapper.toResponse("Successfully fetched order detail",order)))
               .orElseThrow(()->OrderNotFoundException.forOrderId(id.toString()));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest orderPayload){
        String username = securityService.getLoginUsername();
        LOGGER.info(username);
        CreateOrderResponse orderResponse = this.orderService.createOrder(orderPayload);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.toResponse("Successfully created new order",orderResponse));
    }


}
