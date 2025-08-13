package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.Repository.InventoryRepository;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final SupplierService supplierService;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository,
                            SupplierService supplierService) {
        this.inventoryRepository = inventoryRepository;
        this.supplierService = supplierService;
    }




    @Transactional
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Inventory getInventoryByIdOrThrow(Long id) {
        return getInventoryById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
    }

    @Transactional
    public Inventory createInventory(Inventory inventory) {
        validateSupplier(inventory.getSupplier());
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryByIdOrThrow(id);

        inventory.setName(inventoryDetails.getName());
        inventory.setCategory(inventoryDetails.getCategory());
        inventory.setPrice(inventoryDetails.getPrice());
        inventory.setStock(inventoryDetails.getStock());
        inventory.setMinStock(inventoryDetails.getMinStock());

        validateSupplier(inventoryDetails.getSupplier());
        inventory.setSupplier(inventoryDetails.getSupplier());

        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void deleteInventory(Long id) {
        Inventory inventory = getInventoryByIdOrThrow(id);
        inventoryRepository.delete(inventory);
    }

    @Transactional(readOnly = true)
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findLowStockItems();
    }

    @Transactional(readOnly = true)
    public List<Inventory> getInventoryByCategory(String category) {
        return inventoryRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Inventory> getInventoryBySupplier(Long supplierId) {
        return inventoryRepository.findBySupplierId(supplierId);
    }

    @Transactional
    public Inventory updateStock(Long id, Integer quantityChange) {
        Inventory inventory = getInventoryByIdOrThrow(id);
        inventory.setStock(inventory.getStock() + quantityChange);
        return inventoryRepository.save(inventory);
    }

    private void validateSupplier(Supplier supplier) {
        if (!supplierService.getSupplierById(supplier.getId()).isPresent()) {
            throw new RuntimeException("Supplier not found with id: " + supplier.getId());
        }
    }
}