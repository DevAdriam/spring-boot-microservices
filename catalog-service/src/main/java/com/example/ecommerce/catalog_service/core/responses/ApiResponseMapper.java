package com.example.ecommerce.catalog_service.core.responses;

import com.example.ecommerce.catalog_service.core.exceptions.ExceptionResponse;
import java.time.Instant;

public class ApiResponseMapper {
    public static <T> ApiResponse<T> toResponse(String message, T data) {
        ExceptionResponse.MetaData metaData = new ExceptionResponse.MetaData(Instant.now(), "v1", "catalog-service");
        return new ApiResponse<>(true, metaData, message, data);
    }
}
