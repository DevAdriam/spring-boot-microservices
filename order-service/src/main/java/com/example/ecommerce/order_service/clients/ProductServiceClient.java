package com.example.ecommerce.order_service.clients;

import java.lang.classfile.ClassFile.Option;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.ecommerce.order_service.ApplicationProperties;
import com.example.ecommerce.order_service.core.exceptions.OrderPayloadInvalidException;
import com.example.ecommerce.order_service.core.responses.ApiResponse;
import com.example.ecommerce.order_service.domain.base_endpoints.BaseEndpoints;


@Component
public class ProductServiceClient {

        private final RestClient restclient;

        public ProductServiceClient(RestClient client ,ApplicationProperties properties ){
                this.restclient = RestClient.builder().baseUrl(properties.catalogServiceURL().concat(BaseEndpoints.CATALOG_ENDPOINT)).build();
        }

        
        public Optional<Product> getProductByCode(String code){
                        var result = restclient
                                        .get()
                                        .uri(uriBuilder -> uriBuilder.path("/" + code).build())
                                        .retrieve()
                                        .body(ApiResponse.class);
                        
                        if(result != null && result.success()){
                                Product product = ProductMapper.toProduct(result.data());
                                return Optional.ofNullable(product);
                        }
                        return Optional.empty();
        }

}
