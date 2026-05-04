package com.miniProject.controller;

import com.miniProject.dto.ProductSearchCriteria;
import com.miniProject.service.CategoryService;
import com.miniProject.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StorefrontController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public StorefrontController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "8") int size,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "minPrice", required = false) String minPrice,
                       @RequestParam(value = "maxPrice", required = false) String maxPrice,
                       @RequestParam(value = "categoryId", required = false) Long categoryId,
                       Model model) {
        ProductSearchCriteria criteria = new ProductSearchCriteria();
        criteria.setName(name);
        criteria.setCategoryId(categoryId);
        if (minPrice != null && !minPrice.isBlank()) {
            try {
                criteria.setMinPrice(new java.math.BigDecimal(minPrice));
            } catch (NumberFormatException ignored) {
                criteria.setMinPrice(null);
            }
        }
        if (maxPrice != null && !maxPrice.isBlank()) {
            try {
                criteria.setMaxPrice(new java.math.BigDecimal(maxPrice));
            } catch (NumberFormatException ignored) {
                criteria.setMaxPrice(null);
            }
        }

        Page<com.miniProject.model.Product> products = productService.search(criteria, page - 1, size);
        model.addAttribute("products", products.getContent());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("criteria", criteria);
        model.addAttribute("categories", categoryService.findAll());
        return "storefront/index";
    }
}
