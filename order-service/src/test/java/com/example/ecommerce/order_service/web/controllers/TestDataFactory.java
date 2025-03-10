package com.example.ecommerce.order_service.web.controllers;
import static org.instancio.Select.field;

import org.instancio.Instancio;

import com.example.ecommerce.order_service.domain.dto.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.dto.Customer;
import com.example.ecommerce.order_service.domain.dto.OrderItems;

import java.util.List;
import java.util.Set;


public class TestDataFactory {

     static final List<String> VALID_LOCATION =List.of("yangon","mandalay");
     static final String[] locations = {"yangon","mandalay"};
     static final Set<OrderItems> VALID_ORDER_ITEMS =  Set.of(
            new OrderItems("P100","test product",100,200));
     static final Set<OrderItems> INVALID_ORDER_ITEMS = Set.of(
            new OrderItems("RX$24",null,-100,-200)
    );

    public static CreateOrderRequest createOrderRequestWithValidPayload (){
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::getName),gen->gen.text().pattern("#c#c#c#c"))
                .generate(field(Customer::getPhone),gen->gen.text().pattern("+95##########"))
                .generate(field(Customer::getEmail), gen->gen.text().pattern("#a#a#a#a#a#a@gmail.com"))
                .set(field(CreateOrderRequest::orderItems),VALID_ORDER_ITEMS)
                .generate(field(CreateOrderRequest::location),gen->gen.oneOf(VALID_LOCATION))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithCustomerInValidPayload (){
        return Instancio.of(CreateOrderRequest.class)
                .set(field(Customer::getName), null)
                .generate(field(Customer::getEmail), gen->gen.text().pattern("#a#a#a#a#a#a"))
                .set(field(CreateOrderRequest::orderItems),VALID_ORDER_ITEMS)
                .generate(field(CreateOrderRequest::location),gen->gen.oneOf(VALID_LOCATION))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithOrderItemsInvalidPayload(){
      return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::getName), gen->gen.text().pattern("#a#a#a"))
                .generate(field(Customer::getEmail), gen->gen.text().pattern("#a#a#a#a@gmail.com"))
                 .set(field(CreateOrderRequest::orderItems), INVALID_ORDER_ITEMS)
                .create();

    }

    public static CreateOrderRequest createOrderRequestWithNoOrderItemsPayload(){
      return Instancio.of(CreateOrderRequest.class)
            .set(field(CreateOrderRequest::orderItems), Set.of()).create();
    }

    public static  final String SUCCESSFUL_ORDER_PAYLOAD =
                            """
                            {
                              "orderItems": [
                                {
                                  "code": "P102",
                                  "name": "Hitlar",
                                  "price": 180,
                                  "quantity": 1
                                }
                              ],
                              "customer": {
                                "name": "mgmg",
                                "email": "mgmg@gmail.com",
                                "phone": "09100200300"
                              },
                              "location": "yangon"
                            }
                    """;

     public static  final String INVALID_CUSTOMER_ORDER_PAYLOAD =
                            """
                            {
                              "orderItems": [
                                {
                                  "code": "randomCode",
                                  "name": "testproduct",
                                  "price": 3999,
                                  "quantity": 23
                                }
                              ],
                              "customer": {
                                "name": "",
                                "email": "",
                                "phone": ""
                              },
                              "location": "yandon"
                            }
                    """;

}
