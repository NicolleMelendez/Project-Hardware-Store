package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EmployeeService employeeService;

    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    public Optional<Entry> getEntryById(Long id) {
        return entryRepository.findById(id);
    }

    public Entry createEntry(Entry entry) {
        // Validar y cargar relaciones
        validateAndLoadRelations(entry);

        // Actualizar stock en inventario
        updateInventoryStock(entry.getInventory(), entry.getAmount());

        // Guardar la entrada
        return entryRepository.save(entry);
    }

    public Entry updateEntry(Long id, Entry entryDetails) {
        Entry existingEntry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));

        // Validar y cargar relaciones
        validateAndLoadRelations(entryDetails);

        // Calcular diferencia de cantidad
        int quantityDifference = entryDetails.getAmount() - existingEntry.getAmount();
        updateInventoryStock(entryDetails.getInventory(), quantityDifference);

        // Actualizar campos
        existingEntry.setInventory(entryDetails.getInventory());
        existingEntry.setSupplier(entryDetails.getSupplier());
        existingEntry.setAmount(entryDetails.getAmount());
        existingEntry.setPriceBuy(entryDetails.getPriceBuy());
        existingEntry.setDateEntry(entryDetails.getDateEntry());
        existingEntry.setEmployee(entryDetails.getEmployee());

        return entryRepository.save(existingEntry);
    }

    public void deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));

        // Revertir stock en inventario
        updateInventoryStock(entry.getInventory(), -entry.getAmount());

        entryRepository.delete(entry);
    }

    public List<Entry> getEntriesByInventory(Long inventoryId) {
        return entryRepository.findByInventoryId(inventoryId);
    }

    public List<Entry> getEntriesBySupplier(Long supplierId) {
        return entryRepository.findBySupplierId(supplierId);
    }

    public List<Entry> getEntriesBetweenDates(Date start, Date end) {
        return entryRepository.findByDateEntryBetween(start, end);
    }

    private void validateAndLoadRelations(Entry entry) {
        // Cargar y validar Inventory
        Inventory inventory = inventoryService.getInventoryById(entry.getInventory().getId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        entry.setInventory(inventory);

        // Cargar y validar Supplier
        Supplier supplier = supplierService.getSupplierById(entry.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        entry.setSupplier(supplier);

        // Cargar y validar Employee
        Employee employee = employeeService.getEmployeeById(entry.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        entry.setEmployee(employee);
    }

    private void updateInventoryStock(Inventory inventory, int quantity) {
        // Primero obtenemos el inventory actualizado de la base de datos
        Inventory managedInventory = inventoryService.getInventoryById(inventory.getId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        // Actualizamos el stock
        managedInventory.setStock(managedInventory.getStock() + quantity);

        // Guardamos los cambios
        inventoryService.saveInventory(managedInventory);
    }
}