package com.example.ecommerce.order_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.order_service.domain.models.OrderEntity;

import java.util.UUID;

interface OrderRepository extends JpaRepository<OrderEntity , UUID> {

}
