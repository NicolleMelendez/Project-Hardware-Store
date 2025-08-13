package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inventoryService.getInventoryByIdOrThrow(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory created = inventoryService.createInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable Long id, @RequestBody Inventory inventory) {
        try {
            return ResponseEntity.ok(inventoryService.updateInventory(id, inventory));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/low-stock")
    public List<Inventory> getLowStockItems() {
        return inventoryService.getLowStockItems();
    }

    @GetMapping("/category/{category}")
    public List<Inventory> getInventoryByCategory(@PathVariable String category) {
        return inventoryService.getInventoryByCategory(category);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<Inventory> getInventoryBySupplier(@PathVariable Long supplierId) {
        return inventoryService.getInventoryBySupplier(supplierId);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Inventory> updateStock(
            @PathVariable Long id, @RequestParam Integer quantity) {
        try {
            return ResponseEntity.ok(inventoryService.updateStock(id, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}