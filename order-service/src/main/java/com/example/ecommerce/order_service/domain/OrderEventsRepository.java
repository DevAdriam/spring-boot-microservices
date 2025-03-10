package com.example.ecommerce.order_service.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.order_service.domain.models.OrderEvents;

interface OrderEventsRepository  extends JpaRepository<OrderEvents, UUID>{
        
}
