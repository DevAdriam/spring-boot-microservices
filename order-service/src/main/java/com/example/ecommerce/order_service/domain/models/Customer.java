package com.example.ecommerce.order_service.domain.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Customer(
        @NotBlank(message = "customer name is required") String name,
        @NotBlank(message = "customer email is required") @Email String email,
        @NotBlank(message = "customer phone is required") String phone
){}