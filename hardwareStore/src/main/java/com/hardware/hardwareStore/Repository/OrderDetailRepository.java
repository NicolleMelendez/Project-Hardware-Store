package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
