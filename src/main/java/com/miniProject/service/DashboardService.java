package com.miniProject.service;

import com.miniProject.repository.OrderDetailRepository;
import com.miniProject.repository.TopProductProjection;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final OrderDetailRepository orderDetailRepository;

    public DashboardService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public BigDecimal getTotalRevenue() {
        return orderDetailRepository.calculateTotalRevenue();
    }

    public List<TopProductProjection> getTopProducts(int limit) {
        return orderDetailRepository.findTopProducts(PageRequest.of(0, limit));
    }
}

