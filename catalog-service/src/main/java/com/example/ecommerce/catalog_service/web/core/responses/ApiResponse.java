package com.example.ecommerce.catalog_service.web.core.responses;

import com.example.ecommerce.catalog_service.web.core.exceptions.ExceptionResponse;

public record ApiResponse<T>(boolean success, ExceptionResponse.MetaData metaData, String message, T data) {}
