package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderBuyId(Long orderId);
}