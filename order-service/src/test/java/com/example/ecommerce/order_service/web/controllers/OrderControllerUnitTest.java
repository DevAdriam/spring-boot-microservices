package com.example.ecommerce.order_service.web.controllers;
import com.example.ecommerce.order_service.domain.OrderService;
import com.example.ecommerce.order_service.domain.SecurityService;
import com.example.ecommerce.order_service.domain.base_endpoints.BaseEndpoints;
import com.example.ecommerce.order_service.domain.models.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.params.ParameterizedTest;


import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Named.named;



@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerUnitTest {

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        given(securityService.getLoginUsername()).willReturn("naingaungzaw");
    }

    // parametrized test enabled single test with different parameters
    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestsProvider")
    void shouldReturnBadRequestWhenOrderPayloadInvalid(CreateOrderRequest request) throws Exception {
       given(orderService.createOrder(any(CreateOrderRequest.class)))
       .willReturn(null);
       mockMvc.perform(post(BaseEndpoints.ORDER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
       ).andExpect(status().isBadRequest());
    }

    static Stream<Arguments> createOrderRequestsProvider(){
        return Stream.of(
            arguments(named("Customer Invalid Payload", TestDataFactory.createOrderRequestWithCustomerInValidPayload())),
            arguments(named("OrderItems Invalid Payload", TestDataFactory.createOrderRequestWithOrderItemsInvalidPayload())),
            arguments(named("No Order items Payload", TestDataFactory.createOrderRequestWithNoOrderItemsPayload()))
        );
    }
}

