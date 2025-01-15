package com.example.ecommerce.catalog_service.core.responses;

import com.example.ecommerce.catalog_service.core.exceptions.ExceptionResponse;

public record ApiResponse<T>(boolean success, ExceptionResponse.MetaData metaData, String message, T data) {}
