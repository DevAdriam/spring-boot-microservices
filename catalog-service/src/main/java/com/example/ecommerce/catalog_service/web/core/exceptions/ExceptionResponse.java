package com.example.ecommerce.catalog_service.web.core.exceptions;

import java.time.Instant;

public record ExceptionResponse(boolean success, MetaData metaData, ErrorDetails errorDetails) {

    public record MetaData(Instant timeStamp, String version, String service) {}

    public record ErrorDetails(String message, String causedBy) {}
}
