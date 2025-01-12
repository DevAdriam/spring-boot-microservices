package com.example.ecommerce.catalog_service.domain;

class ProductMapper {

    static Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getCode(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getStocks(),
                productEntity.getPrice());
    }
}
