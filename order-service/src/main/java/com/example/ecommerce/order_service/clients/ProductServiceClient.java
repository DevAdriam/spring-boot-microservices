package com.example.ecommerce.order_service.clients;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.ecommerce.order_service.core.responses.ApiResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@AllArgsConstructor
@Slf4j
public class ProductServiceClient {

        private RestClient restClient;
        
        @CircuitBreaker(name = "catalog-service",fallbackMethod = "getProductByCodeFallback")
        @Retry(name = "catalog-service",fallbackMethod = "getProductByCodeFallback")
        public Optional<Product> getProductByCode(String code){
                        var result = restClient
                                        .get()
                                        .uri(uriBuilder -> uriBuilder.path("/api/v1/products/" + code).build())
                                        .retrieve()
                                        .body(ApiResponse.class);
                        if(result != null && result.success()){
                                Product product = ProductMapper.toProduct(result.data());
                                return Optional.ofNullable(product);
                        }
                        return Optional.empty();
        }

        Optional<Product> getProductByCodeFallback(String code , Throwable t){
                // ? --- fallback option ---
                System.out.println(code);
                log.error("Failed to get product -> code : " + code);
                return Optional.empty();
        }

}
