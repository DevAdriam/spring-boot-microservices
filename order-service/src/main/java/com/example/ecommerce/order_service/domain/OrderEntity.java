package com.example.ecommerce.order_service.domain;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.example.ecommerce.order_service.domain.models.Customer;
import com.example.ecommerce.order_service.domain.models.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id",updatable = false,nullable = false)
        private UUID id;

        @Column(name = "order_no" ,nullable = false)
        private String orderNo;

        @Column(name = "total",nullable = false)
        private double total;
        
        @Column(name = "sub_total",nullable = false)
        private double sub_total;

        @Embedded
        @AttributeOverrides(
                value = {
                        @AttributeOverride(name = "name",column = @Column(name = "customername",nullable = false)),
                        @AttributeOverride(name = "email",column = @Column(name = "customeremail",nullable = false)),
                        @AttributeOverride(name = "phone",column = @Column(name = "customerphone",nullable = false))
                }
        )
        private Customer customer;

        @Column(name = "location",nullable = false)
        private String location;

        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "orderstatus")
        @JdbcTypeCode(SqlTypes.NAMED_ENUM)
        private OrderStatus orderStatus;

        @CurrentTimestamp
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL,orphanRemoval = true)
        private Set<OrderItemsEntity> orderItems;

        public void calculatSubTotalPrice(){
                this.sub_total = orderItems.stream()
                                        .mapToDouble(item-> item.getPerPrice() * item.getQuantity()).sum();
        }
        
        public void setOrderNo(){
                this.orderNo =UUID.randomUUID().toString();
        }

        public void setOrderItems(Set<OrderItemsEntity> orderitems){
                this.orderItems = orderitems;
                calculatSubTotalPrice();
        }

        @PrePersist
        @PreUpdate
        public void updateSubTotal(){
                calculatSubTotalPrice();
        }

}
