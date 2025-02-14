package com.example.ecommerce.order_service.domain;

import com.example.ecommerce.order_service.domain.models.CreateOrderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ecommerce.order_service.domain.models.CreateOrderRequest;
import com.example.ecommerce.order_service.domain.models.OrderStatus;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

        private final OrderRepository orderRepository;
        private final Logger logger = LoggerFactory.getLogger(OrderService.class);

        private static final  int taxFee = 100;
        private static final int deliveryFee = 200;

        public CreateOrderResponse createOrder(CreateOrderRequest request){
               OrderEntity orderEntity = OrderMapper.toOrderEntity(request);
               System.out.println(orderEntity.getCustomer());
               orderEntity.setOrderNo();
               orderEntity.setOrderStatus(OrderStatus.PENDING);
               orderEntity.setTotal(orderEntity.getSub_total() + deliveryFee + taxFee);
                for (OrderItemsEntity item : orderEntity.getOrderItems()) {
                         item.setOrders(orderEntity); 
                }   
               OrderEntity saveOrder =  this.orderRepository.save(orderEntity);
               logger.info("New order created successfully -> {}",orderEntity.getOrderNo());
               return new CreateOrderResponse(saveOrder.getOrderNo());
        };


        public Optional<OrderEntity> findOrderById(UUID id ){
           return this.orderRepository.findById(id);
        }


}
