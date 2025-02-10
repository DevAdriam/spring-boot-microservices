package com.example.ecommerce.order_service.core.responses;
import com.example.ecommerce.order_service.core.exceptions.ExceptionResponse.MetaData;

import java.time.Instant;

public class ResponseMapper {

    private static final String service = "order-service";

   public static <T>ApiResponse<T> toResponse(String message , T data){
        boolean success = true;
        MetaData metaData = new MetaData(Instant.now(),"v1",service);
        return new ApiResponse<>(success,data,message,metaData);
    }

}
