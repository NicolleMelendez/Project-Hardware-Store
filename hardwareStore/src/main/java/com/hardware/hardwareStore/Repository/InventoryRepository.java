package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    @Query("SELECT i FROM Inventory i WHERE i.stock < i.minStock")
    List<Inventory> findLowStockItems();

    List<Inventory> findByCategory(String category);

    @Query("SELECT i FROM Inventory i WHERE i.supplier.id = :supplierId")
    List<Inventory> findBySupplierId(@Param("supplierId") Long supplierId);
}

