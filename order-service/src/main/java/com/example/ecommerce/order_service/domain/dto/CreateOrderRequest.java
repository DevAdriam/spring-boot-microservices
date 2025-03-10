package com.example.ecommerce.order_service.domain.dto;
import java.util.Set;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
        @Valid @NotNull @Size(min = 1,message = "at least one product is required") Set<OrderItems> orderItems,
        @Valid Customer customer,
        @NotBlank(message = "location is required") String location
) {
}
