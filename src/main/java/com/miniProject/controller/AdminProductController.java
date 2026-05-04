package com.miniProject.controller;

import com.miniProject.model.Product;
import com.miniProject.service.CategoryService;
import com.miniProject.service.FileStorageService;
import com.miniProject.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;

    public AdminProductController(ProductService productService,
                                  CategoryService categoryService,
                                  FileStorageService fileStorageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/product-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") Product product,
                       BindingResult result,
                       @RequestParam("categoryId") Long categoryId,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/product-form";
        }
        Product existing = null;
        if (product.getId() != null) {
            existing = productService.findById(product.getId());
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageService.store(imageFile);
            product.setImageUrl(imageUrl);
        } else if (existing != null) {
            product.setImageUrl(existing.getImageUrl());
        }
        product.setCategory(categoryService.findById(categoryId));
        productService.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}
