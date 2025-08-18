package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        validateAndLoadRelations(entry);
        updateInventoryStock(entry.getInventory(), entry.getAmount());
        return entryRepository.save(entry);
    }

    public Entry updateEntry(Long id, Entry entryDetails) {
        Entry existingEntry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la entrada"));

        validateAndLoadRelations(entryDetails);
        int quantityDifference = entryDetails.getAmount() - existingEntry.getAmount();
        updateInventoryStock(entryDetails.getInventory(), quantityDifference);

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
                .orElseThrow(() -> new RuntimeException("No se encontro la entrada"));

        updateInventoryStock(entry.getInventory(), -entry.getAmount());
        entryRepository.delete(entry);
    }

    private void validateAndLoadRelations(Entry entry) {
        // Validar Inventory
        if (entry.getInventory() == null || entry.getInventory().getId() == null) {
            throw new RuntimeException("Debe seleccionar un inventario válido");
        }
        Inventory inventory = inventoryService.getInventoryById(entry.getInventory().getId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        entry.setInventory(inventory);

        // Validar Supplier
        if (entry.getSupplier() == null || entry.getSupplier().getId() == null) {
            throw new RuntimeException("Debe seleccionar un proveedor válido");
        }
        Supplier supplier = supplierService.getSupplierById(entry.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        entry.setSupplier(supplier);

        // Validar Employee
        if (entry.getEmployee() == null || entry.getEmployee().getId() == null) {
            throw new RuntimeException("Debe seleccionar un empleado válido");
        }
        Employee employee = employeeService.getEmployeeById(entry.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        entry.setEmployee(employee);
    }

    private void updateInventoryStock(Inventory inventory, int amount) {
        Inventory managedInventory = inventoryService.getInventoryById(inventory.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el producto"));

        managedInventory.setStock(managedInventory.getStock() + amount);
        inventoryService.saveInventory(managedInventory);
    }
}