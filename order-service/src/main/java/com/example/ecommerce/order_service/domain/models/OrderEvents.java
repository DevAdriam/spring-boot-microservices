package com.example.ecommerce.order_service.domain.models;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.example.ecommerce.order_service.domain.dto.OrderEventsType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_events")
public class OrderEvents {
        
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Enumerated(EnumType.STRING)
        @Column(name = "event_status")
        @JdbcTypeCode(SqlTypes.NAMED_ENUM)
       private OrderEventsType orderEventStatus;

       @OneToOne(mappedBy = "orderEvents" )
       private  OrderEntity orderEntity; 

        @CurrentTimestamp
        @Column(name = "created_at")
        private Instant createdAt;

        @UpdateTimestamp 
        @Column(name = "updated_at")
        private Instant updatedAt;
}
