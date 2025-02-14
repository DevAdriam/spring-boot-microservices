package com.example.ecommerce.order_service.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.example.ecommerce.order_service.ApplicationProperties;

@Configuration
public class CatalogServiceClientConfig {

        @Bean
        RestClient restClient(ApplicationProperties properties){
                return RestClient.builder().baseUrl(properties.catalogServiceURL()).build();
        }
         
}