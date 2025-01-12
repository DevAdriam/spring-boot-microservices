package com.example.ecommerce.catalog_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

@Table(name = "products")
@Entity
@Getter
@Setter
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    @NotNull(message = "name is required") private String name;

    @Column(name = "code")
    @NotNull(message = "code is required") private String code;

    @Column(name = "description")
    @NotNull(message = "description is required") private String description;

    @Column(name = "image_url")
    @NotNull(message = "image path is required") private String imageUrl;

    @Column(name = "stocks")
    @NotNull(message = "stocks is required") private int stocks;

    @Column(name = "price")
    @NotNull(message = "price is required") private float price;

    @CurrentTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
