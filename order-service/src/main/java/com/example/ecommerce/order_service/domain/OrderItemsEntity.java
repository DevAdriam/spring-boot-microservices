package com.example.ecommerce.order_service.domain;
import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table(name = "order_items")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsEntity {
        
        @Id
        @GeneratedValue(strategy = GenerationType.UUID )
        private String id ;

        @Column(name = "product_code",nullable = false)
        private String productCode;

        @Column(name = "quantity",nullable = false)
        private int quantity;

        @Column(name = "per_price",nullable = false)
        private double perPrice;

        @Column(name = "total_price",nullable = false)
        private double totalPrice;

        @CurrentTimestamp
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @ManyToOne
        @JoinColumn(name = "order_id")
        private OrderEntity orders;
}
