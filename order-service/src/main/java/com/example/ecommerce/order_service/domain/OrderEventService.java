package com.example.ecommerce.order_service.domain;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.order_service.domain.dto.OrderCreatedEvent;
import com.example.ecommerce.order_service.domain.dto.OrderEventsType;
import com.example.ecommerce.order_service.domain.mapper.OrderMapper;
import com.example.ecommerce.order_service.domain.models.OrderEntity;
import com.example.ecommerce.order_service.domain.models.OrderEvents;
import com.example.ecommerce.order_service.domain.models.OrderEventsPublisher;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderEventService {

        private OrderEventsRepository orderEventsRepository;
        private OrderEventsPublisher orderEventsPublisher;
        private OrderRepository orderRepository;
        private OrderMapper orderMapper;

        public OrderEvents saveOrderEvents(){
                OrderEvents orderEvent = new OrderEvents();
                orderEvent.setOrderEventStatus(OrderEventsType.ORDER_CREATED);
              orderEventsRepository.save(orderEvent);
              return orderEvent;        
        }

        public void publishOrderEvent(){
                List<OrderEntity> orderList = orderRepository.findAll(Sort.by(Sort.Direction.ASC,"createdAt"));
                for(OrderEntity orderEntity : orderList){
                        OrderCreatedEvent orderCreatedEvent = orderMapper.toOrderCreatedEvent(orderEntity);
                        orderEventsPublisher.publishOrderEvent(orderCreatedEvent);
                }
        }
        
        
}
