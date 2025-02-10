package com.example.ecommerce.order_service.core.responses;
import com.example.ecommerce.order_service.core.exceptions.ExceptionResponse;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message,
        ExceptionResponse.MetaData metadata
) {
}
