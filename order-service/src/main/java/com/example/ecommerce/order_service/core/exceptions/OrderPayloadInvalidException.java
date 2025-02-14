package com.example.ecommerce.order_service.core.exceptions;

public class OrderPayloadInvalidException extends RuntimeException{

        public OrderPayloadInvalidException(String message){
                super(message);
        }
}
