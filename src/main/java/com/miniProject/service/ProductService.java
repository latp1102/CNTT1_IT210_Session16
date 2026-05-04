package com.miniProject.service;

import com.miniProject.dto.ProductSearchCriteria;
import com.miniProject.exception.ResourceNotFoundException;
import com.miniProject.model.Product;
import com.miniProject.repository.ProductRepository;
import com.miniProject.specification.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> search(ProductSearchCriteria criteria, int page, int size) {
        Specification<Product> spec = Specification.where(ProductSpecifications.nameContains(criteria.getName()))
                .and(ProductSpecifications.minPrice(criteria.getMinPrice()))
                .and(ProductSpecifications.maxPrice(criteria.getMaxPrice()))
                .and(ProductSpecifications.categoryEquals(criteria.getCategoryId()));

        return productRepository.findAll(spec, PageRequest.of(page, size));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

