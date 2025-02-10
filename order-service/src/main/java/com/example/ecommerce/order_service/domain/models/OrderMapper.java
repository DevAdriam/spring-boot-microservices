package com.example.ecommerce.order_service.domain.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.ecommerce.order_service.domain.OrderEntity;
import com.example.ecommerce.order_service.domain.OrderItemsEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "orderNo", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "total",ignore = true)
    @Mapping(target = "sub_total",ignore = true)
    OrderEntity toEntity(CreateOrderRequest createOrderRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "productCode",source = "code")
    @Mapping(target = "perPrice",source = "price")
    @Mapping(target = "quantity",source = "quantity")
    @Mapping(target = "totalPrice", expression = "java(orderItems.price() * orderItems.quantity())")
    OrderItemsEntity toEntity(OrderItems orderItems);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    Customer toEntity(Customer customer);
}
