package com.miniProject.specification;

import com.miniProject.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> nameContains(String name) {
        return (root, query, builder) -> {
            if (name == null || name.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> categoryEquals(Long categoryId) {
        return (root, query, builder) -> {
            if (categoryId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Product> minPrice(BigDecimal minPrice) {
        return (root, query, builder) -> {
            if (minPrice == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice) {
        return (root, query, builder) -> {
            if (maxPrice == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}

