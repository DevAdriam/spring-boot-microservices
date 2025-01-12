package com.example.ecommerce.catalog_service.domain;

public record Product(
        String id, String name, String code, String description, String imageUrl, int stocks, double price) {}
