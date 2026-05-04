package com.miniProject.service;

import com.miniProject.exception.ResourceNotFoundException;
import com.miniProject.model.Category;
import com.miniProject.repository.CategoryRepository;
import com.miniProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public boolean hasProducts(Long categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}

