package com.example.ecommerce.order_service.clients;

import java.time.Duration;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import com.example.ecommerce.order_service.ApplicationProperties;

@Configuration
public class CatalogServiceClientConfig {

        
        @Bean
        public RestClient restclient(ApplicationProperties properties){
         return RestClient.builder()
                 .baseUrl(properties.catalogServiceURL())
                 .requestFactory(customRequestFactory())
                .build();
        };

        @Bean
        ClientHttpRequestFactory customRequestFactory(){
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(5))
                .withReadTimeout(Duration.ofSeconds(5));
        return ClientHttpRequestFactories.get(settings);
        }
  
}
