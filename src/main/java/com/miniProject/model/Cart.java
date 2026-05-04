package com.miniProject.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public CartItem getItem(Long productId) {
        return items.get(productId);
    }

    public void addItem(Long productId, String productName, String imageUrl, BigDecimal unitPrice, int quantity) {
        CartItem existing = items.get(productId);
        if (existing == null) {
            items.put(productId, new CartItem(productId, productName, imageUrl, unitPrice, quantity));
        } else {
            existing.setQuantity(existing.getQuantity() + quantity);
        }
    }

    public void updateItem(Long productId, int quantity) {
        CartItem existing = items.get(productId);
        if (existing != null) {
            if (quantity <= 0) {
                items.remove(productId);
            } else {
                existing.setQuantity(quantity);
            }
        }
    }

    public void removeItem(Long productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public BigDecimal getTotalAmount() {
        return items.values().stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
