package com.example.ecommerce.order_service.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.order_service.domain.dto.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.dto.CreateOrderResponse;
import com.example.ecommerce.order_service.domain.dto.OrderCreatedEvent;
import com.example.ecommerce.order_service.domain.dto.OrderStatus;
import com.example.ecommerce.order_service.domain.mapper.OrderMapper;
import com.example.ecommerce.order_service.domain.models.OrderEntity;
import com.example.ecommerce.order_service.domain.models.OrderEvents;
import com.example.ecommerce.order_service.domain.models.OrderItemsEntity;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OrderService {

        private final OrderRepository orderRepository;
        private final OrderEventService orderEventService;
        private final OrderMapper orderMapper;

        private static final int taxFee = 100;
        private static final int deliveryFee = 200;

        public CreateOrderResponse createOrder(CreateOrderRequest request){
               OrderEntity orderEntity = orderMapper.toOrderEntity(request);
               orderEntity.setOrderNo();
               orderEntity.setOrderStatus(OrderStatus.PENDING);
               orderEntity.setTotal(orderEntity.getSub_total() + deliveryFee + taxFee);
                for (OrderItemsEntity item : orderEntity.getOrderItems()) {
                         item.setOrders(orderEntity); 
                }   
                OrderEvents orderEvents = orderEventService.saveOrderEvents();
                orderEntity.setOrderEvents(orderEvents);
               OrderEntity saveOrder =  orderRepository.save(orderEntity);
               log.info("New order created successfully -> {}",orderEntity.getOrderNo());
               return new CreateOrderResponse(saveOrder.getOrderNo());
        };

        public List<OrderCreatedEvent> getOrderList(Sort sort){
                return this.orderRepository.findAll(sort)
                                        .stream()
                                                .map(order-> orderMapper.toOrderCreatedEvent(order)).toList();
        }

        public Optional<OrderEntity> findOrderById(UUID id ){
           return this.orderRepository.findById(id);
        }

}
