package com.example.ecommerce.catalog_service.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"})
@Sql(value = "/test-data.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        assertThat(productEntityList).hasSize(3);
    }

    @Test
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P101").orElseThrow();
        assertThat(product.getCode()).isEqualTo("P101");
        assertThat(product.getName()).isEqualTo("Stalin");
        assertThat(product.getDescription()).isEqualTo("European History");
        assertThat(product.getPrice()).isCloseTo(100.29f, within(0.001f));
    }

    @Test
    void shouldReturnEmptyWhenProductCodeNotFound() {
        assertThat(productRepository.findByCode(("invalid_code"))).isEmpty();
    }
}
