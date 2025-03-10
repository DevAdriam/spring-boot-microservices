package com.example.ecommerce.order_service;

import io.restassured.RestAssured;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import com.github.tomakehurst.wiremock.client.WireMock;

import groovy.util.logging.Slf4j;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
@SpringBootTest(webEnvironment =  RANDOM_PORT)
@Import(TestContainersConfiguration.class)
public  abstract class AbstractIntegration {

    @LocalServerPort
    int port;

    static  WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

    @BeforeAll
    static void beforeAll(){
        wiremockServer.start();
        // tell wiremock to dynamically assign host 
        configureFor("localhost",8084);
    }

     @AfterAll
    static void afterAll() {
        if (wiremockServer != null) {
            wiremockServer.stop();
        }
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("orders.catalog-service-url", wiremockServer::getBaseUrl);
        System.out.println(wiremockServer.getBaseUrl());
        System.out.println(wiremockServer.getPort());

    };

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    protected static void mockGetProductByCode(String code , String name , Float price){
            stubFor(WireMock.get(urlMatching("api/v1/products/" + code))
                    .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withStatus(200)
                    .withBody(
                      """
                {
                    "success": true,
                    "metaData": {
                        "timeStamp": "2025-03-02T10:22:36.353899Z",
                        "version": "v1",
                        "service": "catalog-service"
                    },
                    "message": "Successfully fetched product detail",
                    "data": {
                        "id": "mocked-uuid",
                        "name": "%s",
                        "code": "%s",
                        "description": "Mocked description",
                        "stocks": 5,
                        "price": %.2f
                    }
                }
                """
                        .formatted(code,name,price)
                    )
                    )
            );

    }

}
