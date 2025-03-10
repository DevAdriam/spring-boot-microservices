package com.example.ecommerce.order_service.core.configs;

import java.time.format.DateTimeFormatter;

import org.springframework.messaging.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppConfig {

        private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        private static final LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER =
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

        @Bean
        public MessageConverter jsonMessageConverter() {
                return new MappingJackson2MessageConverter();
        }

        @Bean
        @Primary
        public ObjectMapper objectMapper(){
                JavaTimeModule module = new JavaTimeModule();
                module.addSerializer(LOCAL_DATETIME_SERIALIZER);
                return new ObjectMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .registerModule(module);
                        
        }

         @Bean
        public OpenAPI swagger(){
                  return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
        }
}
