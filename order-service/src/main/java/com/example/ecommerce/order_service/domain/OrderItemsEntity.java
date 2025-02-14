package com.example.ecommerce.order_service.domain;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemsEntity {
        
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @JsonProperty("code")
        @Column(name = "product_code",nullable = false)
        private String productCode;

        @JsonProperty("quantity")
        @Column(name = "quantity",nullable = false)
        private int quantity;

        @JsonProperty("price")
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
        @JoinColumn(name = "order_id",nullable = false)
        private OrderEntity orders;

        public void calculateTotalPrice(){
                this.totalPrice = this.perPrice * this.quantity;
        }

        public void setTotalPrice(){
                calculateTotalPrice();
        }

        @PrePersist
        @PreUpdate
        public void updateTotalPrice(){
                calculateTotalPrice();
        }
}
