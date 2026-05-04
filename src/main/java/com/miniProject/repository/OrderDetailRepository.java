package com.miniProject.repository;

import com.miniProject.model.OrderDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select coalesce(sum(od.unitPrice * od.quantity), 0) from OrderDetail od")
    BigDecimal calculateTotalRevenue();

    @Query("select od.product.id as productId, od.product.name as productName, od.product.imageUrl as imageUrl, sum(od.quantity) as totalQuantity " +
            "from OrderDetail od group by od.product.id, od.product.name, od.product.imageUrl order by totalQuantity desc")
    List<TopProductProjection> findTopProducts(Pageable pageable);
}
