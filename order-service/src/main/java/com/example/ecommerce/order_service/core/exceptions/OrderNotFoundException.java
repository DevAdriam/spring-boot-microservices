package com.example.ecommerce.order_service.core.exceptions;


public class OrderNotFoundException extends RuntimeException{
        
        public OrderNotFoundException(String message){
                super(message);
        }

        public static OrderNotFoundException forOrderId(String orderId){
                return new OrderNotFoundException(
                        "Order not found with code " + orderId
                );
        }
}
