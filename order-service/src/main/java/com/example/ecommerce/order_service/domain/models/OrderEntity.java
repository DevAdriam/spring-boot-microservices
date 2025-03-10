package com.example.ecommerce.order_service.domain.models;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.example.ecommerce.order_service.domain.dto.Customer;
import com.example.ecommerce.order_service.domain.dto.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
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

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "order_events_id",referencedColumnName = "id")
        private OrderEvents orderEvents;

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
