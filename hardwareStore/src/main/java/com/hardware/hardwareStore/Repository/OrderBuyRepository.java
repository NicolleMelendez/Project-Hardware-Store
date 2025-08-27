package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.OrderBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderBuyRepository extends JpaRepository<OrderBuy, Long> {

    @Query("SELECT o FROM OrderBuy o LEFT JOIN FETCH o.supplier LEFT JOIN FETCH o.employee ORDER BY o.id DESC")
    List<OrderBuy> findAllWithDetails();

    @Query("SELECT o FROM OrderBuy o LEFT JOIN FETCH o.supplier LEFT JOIN FETCH o.employee WHERE o.id = :id")
    Optional<OrderBuy> findByIdWithDetails(Long id);
}