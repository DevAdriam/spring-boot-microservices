package com.example.ecommerce.order_service.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductMapper {

        static Product toProduct(Object data){
             try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.convertValue(data, Product.class);
             } catch (Exception e) {
                throw new RuntimeException("failed to extract value");
             }
        }
        
}
