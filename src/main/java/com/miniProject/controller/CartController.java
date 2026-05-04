package com.miniProject.controller;

import com.miniProject.model.Cart;
import com.miniProject.model.Product;
import com.miniProject.service.CartService;
import com.miniProject.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "success", required = false) String success) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("errorMessage", error);
        model.addAttribute("successMessage", success);
        return "storefront/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Product product = productService.findById(productId);
        if (quantity <= 0) {
            redirectAttributes.addAttribute("error", "So luong khong hop le");
            return "redirect:/cart";
        }
        if (product.getStockQuantity() < quantity) {
            redirectAttributes.addAttribute("error", "San pham " + product.getName() + " khong du so luong");
            return "redirect:/cart";
        }
        cartService.addItem(session, product, quantity);
        redirectAttributes.addAttribute("success", "Da them san pham vao gio hang");
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateItem(@RequestParam("productId") Long productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        cartService.updateItem(session, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam("productId") Long productId,
                             HttpSession session) {
        cartService.removeItem(session, productId);
        return "redirect:/cart";
    }
}
