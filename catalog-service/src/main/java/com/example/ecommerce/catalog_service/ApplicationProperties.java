package com.example.ecommerce.catalog_service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "catalog")
public record ApplicationProperties(
        @DefaultValue("10")
                @Max(value = 1000, message = "request size too large")
                @Min(value = 1, message = "size must not be less than 1")
                int pageSize) {}
