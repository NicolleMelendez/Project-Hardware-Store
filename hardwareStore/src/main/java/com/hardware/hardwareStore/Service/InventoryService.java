package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.Repository.InventoryRepository;
// IMPORT CORRECTO
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory save(Inventory inventory) {
        // Aquí podrías agregar lógica de negocio antes de guardar.
        // Por ejemplo, validar que el precio no sea negativo.
        if (inventory.getPrice() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo.");
        }
        return inventoryRepository.save(inventory);
    }

    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    /**
     * Obtiene la lista de productos con bajo stock llamando al método personalizado del repositorio.
     * @return Lista de inventario con stock bajo.
     */
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findLowStockProducts();
    }

    @Transactional
    public void updateStock(Long inventoryId, int amount) {
        // 1. Buscamos el producto en la base de datos
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + inventoryId));

        // 2. Calculamos el nuevo stock
        int newStock = inventory.getStock() + amount;

        // 3. Verificamos que el stock no sea negativo
        if (newStock < 0) {
            throw new IllegalStateException("No hay suficiente stock para el producto: " + inventory.getName());
        }

        // 4. Asignamos el nuevo valor y guardamos
        inventory.setStock(newStock);
        inventoryRepository.save(inventory);
    }
}