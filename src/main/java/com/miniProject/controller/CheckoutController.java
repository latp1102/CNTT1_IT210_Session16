package com.miniProject.controller;

import com.miniProject.dto.CheckoutForm;
import com.miniProject.exception.InsufficientStockException;
import com.miniProject.model.Cart;
import com.miniProject.model.Order;
import com.miniProject.service.CartService;
import com.miniProject.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;

    public CheckoutController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkoutForm(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("checkoutForm", new CheckoutForm());
        model.addAttribute("cart", cart);
        return "storefront/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@Valid CheckoutForm checkoutForm,
                                  BindingResult result,
                                  HttpSession session,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            return "storefront/checkout";
        }
        try {
            Order order = orderService.placeOrder(checkoutForm, cart);
            cartService.clear(session);
            model.addAttribute("order", order);
            return "storefront/order-success";
        } catch (InsufficientStockException ex) {
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/cart";
        }
    }
}
