package com.example.ecommerce.catalog_service.web.controllers;

import com.example.ecommerce.catalog_service.core.responses.ApiResponse;
import com.example.ecommerce.catalog_service.core.responses.ApiResponseMapper;
import com.example.ecommerce.catalog_service.domain.PaginatedResult;
import com.example.ecommerce.catalog_service.domain.Product;
import com.example.ecommerce.catalog_service.domain.ProductNotFoundException;
import com.example.ecommerce.catalog_service.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<ApiResponse<PaginatedResult<Product>>> fetchProductList(
            @RequestParam(value = "page", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PaginatedResult<Product> paginatedResult = productService.fetchProductListWithPagination(pageNo, pageSize);
        return ResponseEntity.ok()
                .body(ApiResponseMapper.toResponse("Successfully fetched product list", paginatedResult));
    }

    @GetMapping("/{code}")
    ResponseEntity<ApiResponse<Product>> fetchProductByCode(@PathVariable String code) {
        System.out.println("refacto");
        return productService
                .fetchProductByCode(code)
                .map(product -> ResponseEntity.ok()
                        .body(ApiResponseMapper.toResponse("Successfully fetched product detail", product)))
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
