package com.example.ecommerce.order_service.web.controllers;

import com.example.ecommerce.order_service.AbstractIntegration;
import com.example.ecommerce.order_service.domain.base_endpoints.BaseEndpoints;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class OrderControllerTest extends AbstractIntegration {

    @Nested
    class  createOrderTests{
        @Test
        void shouldBeSuccessfullyOrder(){
            mockGetProductByCode("P102", "Hitlar", (float)180);
            var payload = TestDataFactory.SUCCESSFUL_ORDER_PAYLOAD;
            given().log().all().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post(BaseEndpoints.ORDER_ENDPOINT)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("data.orderNo",notNullValue());
        }

        @Test
        void shouldReturnBadRequestWithInvalidPayload(){
            var payload = TestDataFactory.INVALID_CUSTOMER_ORDER_PAYLOAD;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post(BaseEndpoints.ORDER_ENDPOINT)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
