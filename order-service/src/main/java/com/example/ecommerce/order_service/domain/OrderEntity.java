package com.example.ecommerce.order_service.domain;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.example.ecommerce.order_service.domain.models.Customer;
import com.example.ecommerce.order_service.domain.models.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

        @Id
        @GeneratedValue(strategy =GenerationType.UUID)
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

        @OneToMany(mappedBy = "orders")
        private List<OrderItemsEntity> orderItems;
}
