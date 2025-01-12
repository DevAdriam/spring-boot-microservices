package com.example.ecommerce.catalog_service.domain;

import java.util.List;

public record PaginatedResult<T>(
        int totalPages,
        Long totalElements,
        int pageNumber,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious,
        List<T> data) {}
