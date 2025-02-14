package com.example.ecommerce.order_service.core.exceptions;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {


    ProblemDetail generateProblemDetail (HttpStatus status , Exception e , String title){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status,e.getMessage());
        problemDetail.setTitle(title);
        problemDetail.setStatus(status);
        problemDetail.setProperty("version","v1");

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleUnhandledException(Exception e) {
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR,e,"INTERNAL_SERVER_ERROR");
        ExceptionResponse exceptionResponse = ExceptionMapper.toErrorResponse(problemDetail);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<ExceptionResponse> handleOrderNotFoundException(Exception e) {
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.NOT_FOUND,e,"Order Not Found");
        ExceptionResponse exceptionResponse = ExceptionMapper.toErrorResponse(problemDetail);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(OrderPayloadInvalidException.class)
    ResponseEntity<ExceptionResponse> handleOrderPayloadInvalidException(Exception e) {
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.BAD_REQUEST,e,"Order Payload Invalid");
        ExceptionResponse exceptionResponse = ExceptionMapper.toErrorResponse(problemDetail);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    @Nullable protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
             String errorMessage = ex.getBindingResult()
                            .getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())  
                            .findFirst()
                            .orElse("Invalid Payload");
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.NOT_FOUND,ex,"Validation Error");
        problemDetail.setDetail(errorMessage);
        ExceptionResponse exceptionResponse = ExceptionMapper.toErrorResponse(problemDetail);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
