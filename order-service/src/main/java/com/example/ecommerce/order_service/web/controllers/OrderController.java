package com.example.ecommerce.order_service.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.order_service.core.exceptions.OrderNotFoundException;
import com.example.ecommerce.order_service.core.responses.ApiResponse;
import com.example.ecommerce.order_service.core.responses.ResponseMapper;
import com.example.ecommerce.order_service.domain.OrderService;
import com.example.ecommerce.order_service.domain.OrderValidator;
import com.example.ecommerce.order_service.domain.base_endpoints.BaseEndpoints;
import com.example.ecommerce.order_service.domain.dto.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.dto.CreateOrderResponse;
import com.example.ecommerce.order_service.domain.dto.OrderCreatedEvent;
import com.example.ecommerce.order_service.domain.mapper.OrderMapper;
import com.example.ecommerce.order_service.domain.models.OrderEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(BaseEndpoints.ORDER_ENDPOINT)
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderValidator orderValidator;
    private final OrderMapper orderMapper;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<OrderEntity>> findOrder(@NotNull @RequestParam("id") UUID id ){
       return orderService
               .findOrderById(id)
               .map(order -> ResponseEntity.ok().body(ResponseMapper.toResponse("Successfully fetched order detail",order)))
               .orElseThrow(()->OrderNotFoundException.forOrderId(id.toString()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<OrderCreatedEvent>>> getOrderList(){
        Sort sortOrderByAscOrder = Sort.by(Sort.Direction.ASC , "createdAt");
        List<OrderCreatedEvent> orderList =  orderService.getOrderList(sortOrderByAscOrder);
        
        return ResponseEntity.ok().body(ResponseMapper.toResponse("Successfully fetched order list", orderList));
    }

    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest orderPayload){
       orderValidator.validateOrder(orderPayload);
        CreateOrderResponse orderResponse = this.orderService.createOrder(orderPayload);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMapper.toResponse("Successfully created new order",orderResponse));
    }

}
