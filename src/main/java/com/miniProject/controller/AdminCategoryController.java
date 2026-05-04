package com.miniProject.controller;

import com.miniProject.model.Category;
import com.miniProject.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("errorMessage", error);
        return "admin/category-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "admin/category-form";
        }
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (categoryService.hasProducts(id)) {
            redirectAttributes.addAttribute("error", "Khong the xoa danh muc vi dang co san pham");
            return "redirect:/admin/categories";
        }
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }
}
