package com.miniProject.service;

import com.miniProject.dto.CheckoutForm;
import com.miniProject.exception.InsufficientStockException;
import com.miniProject.model.*;
import com.miniProject.repository.OrderDetailRepository;
import com.miniProject.repository.OrderRepository;
import com.miniProject.repository.ProductRepository;
import com.miniProject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderService(UserRepository userRepository,
                        OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order placeOrder(CheckoutForm form, Cart cart) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        User user = new User();
        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setAddress(form.getAddress());
        User savedUser = userRepository.save(user);

        Order order = new Order();
        order.setUser(savedUser);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("NEW");
        order.setTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("San pham " + product.getName() + " khong du so luong");
            }
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);

            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setProduct(product);
            detail.setUnitPrice(product.getPrice());
            detail.setQuantity(item.getQuantity());
            details.add(detail);
        }
        orderDetailRepository.saveAll(details);
        savedOrder.setOrderDetails(details);

        BigDecimal total = details.stream()
                .map(d -> d.getUnitPrice().multiply(BigDecimal.valueOf(d.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        savedOrder.setTotalAmount(total);

        return orderRepository.save(savedOrder);
    }
}
