package com.example.ecommerce.order_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderItems(
        @NotBlank(message = "code is required") String code,
        @NotBlank(message = "name is required") String name,
        @NotNull(message = "price is required") @Min(1) float price,
        @NotNull @Min(1) int quantity
) {
}
