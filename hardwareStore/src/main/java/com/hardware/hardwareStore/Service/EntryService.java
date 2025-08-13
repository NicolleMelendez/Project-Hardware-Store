package com.hardware.hardwareStore.Service;


import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final InventoryService inventoryService;
    private final SupplierService supplierService;
    private final EmployeeService employeeService;

    @Autowired
    public EntryService(EntryRepository entryRepository,
                        InventoryService inventoryService,
                        SupplierService supplierService,
                        EmployeeService employeeService) {
        this.entryRepository = entryRepository;
        this.inventoryService = inventoryService;
        this.supplierService = supplierService;
        this.employeeService = employeeService;
    }

    @Transactional(readOnly = true)
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Entry> getEntryById(Long id) {
        return entryRepository.findById(id);
    }

    @Transactional
    public Entry createEntry(Entry entry) {
        validateRelations(entry);
        updateInventoryStock(entry.getInventory(), entry.getAmount());
        return entryRepository.save(entry);
    }

    @Transactional
    public Entry updateEntry(Long id, Entry entryDetails) {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));

        validateRelations(entryDetails);
        int quantityDifference = entryDetails.getAmount() - entry.getAmount();
        updateInventoryStock(entryDetails.getInventory(), quantityDifference);

        entry.setInventory(entryDetails.getInventory());
        entry.setSupplier(entryDetails.getSupplier());
        entry.setAmount(entryDetails.getAmount());
        entry.setPriceBuy(entryDetails.getPriceBuy());
        entry.setEmployee(entryDetails.getEmployee());

        return entryRepository.save(entry);
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));

        updateInventoryStock(entry.getInventory(), -entry.getAmount());
        entryRepository.delete(entry);
    }

    @Transactional(readOnly = true)
    public List<Entry> getEntriesByInventory(Long inventoryId) {
        return entryRepository.findByInventoryId(inventoryId);
    }

    @Transactional(readOnly = true)
    public List<Entry> getEntriesBySupplier(Long supplierId) {
        return entryRepository.findBySupplierId(supplierId);
    }

    @Transactional(readOnly = true)
    public List<Entry> getEntriesBetweenDates(Date start, Date end) {
        return entryRepository.findByDateEntryBetween(start, end);
    }

    private void validateRelations(Entry entry) {
        inventoryService.getInventoryByIdOrThrow(entry.getInventory().getId());
        supplierService.getSupplierById(entry.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        employeeService.getEmployeeById(entry.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    private void updateInventoryStock(Inventory inventory, int quantity) {
        Inventory existing = inventoryService.getInventoryByIdOrThrow(inventory.getId());
        existing.setStock(existing.getStock() + quantity);
        inventoryService.updateInventory(existing.getId(), existing);
    }
}
