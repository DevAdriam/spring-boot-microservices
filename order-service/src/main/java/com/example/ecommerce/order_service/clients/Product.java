package com.example.ecommerce.order_service.clients;

public record Product(
        String id, String name, String code, String description, String imageUrl, int stocks, double price) {}