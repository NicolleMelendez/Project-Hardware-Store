package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.OrderBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OrderBuyRepository extends JpaRepository<OrderBuy, Long>{
    // Métodos básicos ya vienen con JpaRepository
    // Si necesitas métodos personalizados con joins, decláralos aquí:

    @Query("SELECT o FROM OrderBuy o JOIN FETCH o.client JOIN FETCH o.employee")
    List<OrderBuy> findAllWithDetails();

    @Query("SELECT o FROM OrderBuy o JOIN FETCH o.client JOIN FETCH o.employee WHERE o.id = :id")
    Optional<OrderBuy> findByIdWithDetails(@Param("id") Long id);

}
