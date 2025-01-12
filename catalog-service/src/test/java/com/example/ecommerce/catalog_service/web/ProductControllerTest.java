package com.example.ecommerce.catalog_service.web;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.hamcrest.Matchers.*;

import com.example.ecommerce.catalog_service.AbstractIntegration;
import com.example.ecommerce.catalog_service.domain.PaginatedResult;
import com.example.ecommerce.catalog_service.domain.Product;
import com.example.ecommerce.catalog_service.web.core.responses.ApiResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = "/test-data.sql")
public class ProductControllerTest extends AbstractIntegration {

    @Test
    void shouldReturnProducts() {
        ApiResponse<PaginatedResult<Product>> apiResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/api/v1/product")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(new TypeRef<ApiResponse<PaginatedResult<Product>>>() {});

        PaginatedResult<Product> paginatedResult = apiResponse.data();

        assertThat(paginatedResult.totalPages()).isEqualTo(1);
        assertThat(paginatedResult.totalElements()).isEqualTo(3);
        assertThat(paginatedResult.hasNext()).isEqualTo(false);
        assertThat(paginatedResult.hasPrevious()).isEqualTo(false);
        assertThat(paginatedResult.isFirst()).isEqualTo(true);
        assertThat(paginatedResult.isLast()).isEqualTo(true);
    }

    @Test
    void shouldReturnProductBycode() {
        ApiResponse<Product> apiResponse = given().contentType(ContentType.JSON)
                .when()
                .get("api/v1/product/{code}", "P101")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(new TypeRef<ApiResponse<Product>>() {});

        Product product = apiResponse.data();
        assertThat(product.code()).isEqualTo("P101");
        assertThat(product.name()).isEqualTo("Stalin");
        assertThat(product.description()).isEqualTo("European History");
        assertThat(product.stocks()).isEqualTo(10);
        assertThat(product.price()).isCloseTo(100.29, within(0.001));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundByCode() {
        String code = "invalid_product_code";
        given().contentType(ContentType.JSON)
                .when()
                .get("api/v1/product/{code}", code)
                .then()
                .statusCode(404)
                .body("success", is(false))
                .body("errorDetails.message", equalTo("Product not found with code " + code));
    }
}
