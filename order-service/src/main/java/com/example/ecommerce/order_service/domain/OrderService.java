package com.example.ecommerce.order_service.domain;

import com.example.ecommerce.order_service.domain.models.CreateOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ecommerce.order_service.domain.models.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.models.OrderMapper;
import com.example.ecommerce.order_service.domain.models.OrderStatus;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

        private final OrderMapper orderMapper;
        private final OrderRepository orderRepository;
        private final Logger logger = LoggerFactory.getLogger(OrderService.class);

        public CreateOrderResponse createOrder(CreateOrderRequest request){
               OrderEntity orderEntity = orderMapper.toEntity(request);
               double totalPrice =  orderEntity.getOrderItems().stream().mapToDouble(OrderItemsEntity::getTotalPrice).sum();
               orderEntity.setTotal(totalPrice);
               orderEntity.setSub_total(totalPrice);
               orderEntity.setOrderStatus(OrderStatus.PENDING);
               OrderEntity saveOrder =  this.orderRepository.save(orderEntity);
               logger.info("New order created successfully -> {}",orderEntity.getOrderNo());
               return new CreateOrderResponse(saveOrder.getOrderNo());
        };


        public Optional<OrderEntity> findOrderById(UUID id ){
           return this.orderRepository.findById(id);
        }
}
