package com.miniProject.service;

import com.miniProject.model.Cart;
import com.miniProject.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "CART";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addItem(HttpSession session, Product product, int quantity) {
        Cart cart = getCart(session);
        cart.addItem(product.getId(), product.getName(), product.getImageUrl(), product.getPrice(), quantity);
    }

    public void updateItem(HttpSession session, Long productId, int quantity) {
        getCart(session).updateItem(productId, quantity);
    }

    public void removeItem(HttpSession session, Long productId) {
        getCart(session).removeItem(productId);
    }

    public void clear(HttpSession session) {
        getCart(session).clear();
    }
}
