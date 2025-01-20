package com.example.ecommerce.order_service.core.exceptions;

import java.time.Instant;

import org.springframework.http.ProblemDetail;

import com.example.ecommerce.order_service.core.exceptions.ExceptionResponse.ErrorDetails;
import com.example.ecommerce.order_service.core.exceptions.ExceptionResponse.MetaData;

public class ExceptionMapper{

        private static final String service = "order-service";

        static ExceptionResponse toErrorResponse(ProblemDetail problemDetail){
                  boolean success = problemDetail.getStatus() >= 200 && problemDetail.getStatus()<300;
                String apiVersion = (String)problemDetail.getProperties().getOrDefault("version", "v1");
                  String causedBy = (String) problemDetail.getProperties().getOrDefault("causedBy", problemDetail.getDetail());
                  String errorMessage = problemDetail.getDetail();
                
                  MetaData metaData = new MetaData(Instant.now(), service,apiVersion);
                  ErrorDetails errorDetails = new ErrorDetails(errorMessage,causedBy);

                return new ExceptionResponse(success, metaData, errorDetails);
        }

}