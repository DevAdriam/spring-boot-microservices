package com.example.ecommerce.order_service.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{
        
    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleUnhandledException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        ExceptionResponse exceptionResponse =ExceptionMapper.toErrorResponse(problemDetail);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<ExceptionResponse> handleProductNotFoundException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Order Not Found");
        problemDetail.setStatus(HttpStatus.NOT_FOUND);

        ExceptionResponse exceptionResponse = ExceptionMapper.toErrorResponse(problemDetail);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
