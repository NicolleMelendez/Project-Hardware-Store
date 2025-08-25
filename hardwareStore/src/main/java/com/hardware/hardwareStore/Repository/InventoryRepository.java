package com.hardware.hardwareStore.Repository;



import com.hardware.hardwareStore.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Encuentra productos cuyo stock actual es menor o igual a su stock mínimo definido.
     * [cite_start]Esta consulta es esencial para el requisito de generar reportes de stock bajo. [cite: 52]
     * @return Una lista de productos con bajo stock.
     */
    @Query("SELECT i FROM Inventory i WHERE i.stock <= i.minStock")
    List<Inventory> findLowStockProducts();
}

