package com.example.ecommerce.order_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface OrderRepository extends JpaRepository<OrderEntity , UUID> {

}
