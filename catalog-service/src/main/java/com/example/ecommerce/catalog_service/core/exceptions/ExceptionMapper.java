package com.example.ecommerce.catalog_service.core.exceptions;

import java.time.Instant;
import org.springframework.http.ProblemDetail;

class ExceptionMapper {

    private static final String SERVICE_NAME = "catalog-service";

    static ExceptionResponse toErrorResponse(ProblemDetail problemDetail) {

        boolean status = problemDetail.getStatus() >= 200 && problemDetail.getStatus() < 300;
        problemDetail.setProperty("service", SERVICE_NAME);

        ExceptionResponse.MetaData metaData = new ExceptionResponse.MetaData(
                Instant.now(), (String) problemDetail.getProperties().getOrDefault("version", "v1"), (String)
                        problemDetail.getProperties().getOrDefault("service", SERVICE_NAME));

        ExceptionResponse.ErrorDetails errorDetails =
                new ExceptionResponse.ErrorDetails(problemDetail.getDetail(), (String)
                        problemDetail.getProperties().getOrDefault("causedBy", problemDetail.getDetail()));

        return new ExceptionResponse(status, metaData, errorDetails);
    }
}
