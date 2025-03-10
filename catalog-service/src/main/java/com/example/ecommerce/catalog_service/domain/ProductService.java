package com.example.ecommerce.catalog_service.domain;

import com.example.ecommerce.catalog_service.ApplicationProperties;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
        this.productRepository = productRepository;
        this.applicationProperties = applicationProperties;
    }

    public PaginatedResult<Product> fetchProductListWithPagination(int pageNo, int pageSize) {
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        pageSize = pageSize > 0 ? pageSize : applicationProperties.pageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "name");
        Page<Product> productsPaginationData =
                productRepository.findAll(pageable).map(ProductMapper::toProduct);

        return new PaginatedResult<Product>(
                productsPaginationData.getTotalPages(),
                productsPaginationData.getTotalElements(),
                productsPaginationData.getNumber() + 1,
                productsPaginationData.isFirst(),
                productsPaginationData.isLast(),
                productsPaginationData.hasNext(),
                productsPaginationData.hasPrevious(),
                productsPaginationData.getContent());
    }

    public Optional<Product> fetchProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
