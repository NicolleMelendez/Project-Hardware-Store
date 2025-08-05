package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
}
