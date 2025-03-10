package com.example.ecommerce.order_service.jobs;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.ecommerce.order_service.domain.OrderService;
import com.example.ecommerce.order_service.domain.dto.OrderCreatedEvent;
import com.example.ecommerce.order_service.domain.mapper.OrderMapper;
import com.example.ecommerce.order_service.domain.models.OrderEntity;
import com.example.ecommerce.order_service.domain.models.OrderEventsPublisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class OrderEventPublishJob {

        private final OrderService orderService;
        private OrderEventsPublisher orderEventsPublisher;
        private OrderMapper orderMapper;

        @Scheduled(cron = "5 * * * * *" ) // Every Minutes of 5 seconds
        public void publishOrderEvents(){
                log.info("Order Publish Event Cron Job Running .....");
                Sort sortOrderByAscOrder = Sort.by(Sort.Direction.ASC , "createdAt");
                List<OrderCreatedEvent> orderList= orderService.getOrderList(sortOrderByAscOrder);

                for(OrderCreatedEvent orderEvent : orderList){
                        orderEventsPublisher.publishOrderEvent(orderEvent);
                }
        }
        
}
