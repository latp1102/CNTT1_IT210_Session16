package com.miniProject.config;

import com.miniProject.model.Category;
import com.miniProject.model.Product;
import com.miniProject.repository.CategoryRepository;
import com.miniProject.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedData(CategoryRepository categoryRepository,
                                      ProductRepository productRepository) {
        return args -> {
            if (categoryRepository.count() > 0 || productRepository.count() > 0) {
                return;
            }

            Category electronics = new Category();
            electronics.setName("Electronics");

            Category fashion = new Category();
            fashion.setName("Fashion");

            Category home = new Category();
            home.setName("Home");

            categoryRepository.saveAll(List.of(electronics, fashion, home));

            productRepository.saveAll(List.of(
                    buildProduct("Wireless Mouse", "Basic wireless mouse", new BigDecimal("12.50"), 50, electronics),
                    buildProduct("Keyboard", "Compact keyboard", new BigDecimal("19.99"), 30, electronics),
                    buildProduct("T-Shirt", "Cotton t-shirt", new BigDecimal("9.90"), 80, fashion),
                    buildProduct("Jeans", "Slim fit jeans", new BigDecimal("25.00"), 40, fashion),
                    buildProduct("Coffee Mug", "Ceramic mug", new BigDecimal("6.50"), 100, home),
                    buildProduct("Desk Lamp", "LED lamp", new BigDecimal("18.00"), 25, home)
            ));
        };
    }

    private Product buildProduct(String name, String description, BigDecimal price, int stock, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stock);
        product.setCategory(category);
        return product;
    }
}

