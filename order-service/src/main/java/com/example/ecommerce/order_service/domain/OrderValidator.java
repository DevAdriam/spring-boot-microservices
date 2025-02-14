package com.example.ecommerce.order_service.domain;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.ecommerce.order_service.clients.Product;
import com.example.ecommerce.order_service.clients.ProductServiceClient;
import com.example.ecommerce.order_service.core.exceptions.OrderPayloadInvalidException;
import com.example.ecommerce.order_service.domain.models.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.models.OrderItems;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderValidator {

        private final ProductServiceClient productServiceClient;

        public void validateOrder(CreateOrderRequest request){
                Set<OrderItems> items = request.orderItems();

                for (OrderItems item : items){
                        Product founProduct = productServiceClient.getProductByCode(item.code())
                        .orElseThrow(() -> new OrderPayloadInvalidException("Invalid product code : "+ item.code()));

                        if(item.price() != founProduct.price()){
                                throw new OrderPayloadInvalidException("Price is not match for product code : " + item.code());
                        }

                        if(item.quantity() > founProduct.stocks()){
                                throw new OrderPayloadInvalidException("Stock is not enough for product code : " + item.code());
                        }
                }

        }
        
}
