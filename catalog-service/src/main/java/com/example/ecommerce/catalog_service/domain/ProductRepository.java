package com.example.ecommerce.catalog_service.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Optional<ProductEntity> findByCode(String code);
}
