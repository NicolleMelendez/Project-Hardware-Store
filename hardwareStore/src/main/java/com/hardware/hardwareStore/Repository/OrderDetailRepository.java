package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import java.util.Map;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderBuyId(Long orderId);

    @Query("SELECT new map(i.name as name, SUM(od.amount) as quantity) FROM OrderDetail od JOIN od.inventory i GROUP BY i.name ORDER BY SUM(od.amount) DESC")
    List<Map<String, Object>> findTopOrderedProducts(Pageable pageable);
}