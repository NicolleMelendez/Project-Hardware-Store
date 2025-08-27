package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Entry;
import com.hardware.hardwareStore.Repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private InventoryService inventoryService;

    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    public Entry createEntry(Entry entry) {
        // Validar que los campos requeridos no sean nulos
        validateEntry(entry);

        // Actualizar el stock del inventario
        if (entry.getInventory() != null && entry.getInventory().getId() != null && entry.getAmount() != null) {
            inventoryService.updateStock(entry.getInventory().getId(), entry.getAmount());
        }

        // Los campos createdAt y updatedAt se auto-generan con @CreationTimestamp y @UpdateTimestamp
        return entryRepository.save(entry);
    }

    public Entry updateEntry(Long id, Entry entryDetails) {
        Optional<Entry> optionalEntry = entryRepository.findById(id);
        if (optionalEntry.isPresent()) {
            Entry entry = optionalEntry.get();

            // Validar los nuevos datos
            validateEntry(entryDetails);

            // Si cambia la cantidad, actualizar el stock
            if (!entry.getAmount().equals(entryDetails.getAmount()) && entry.getInventory() != null) {
                int difference = entryDetails.getAmount() - entry.getAmount();
                inventoryService.updateStock(entry.getInventory().getId(), difference);
            }

            // Actualizar campos
            entry.setInventory(entryDetails.getInventory());
            entry.setSupplier(entryDetails.getSupplier());
            entry.setAmount(entryDetails.getAmount());
            entry.setPriceBuy(entryDetails.getPriceBuy());
            entry.setDateEntry(entryDetails.getDateEntry());
            entry.setEmployee(entryDetails.getEmployee());

            // updatedAt se auto-actualiza con @UpdateTimestamp
            return entryRepository.save(entry);
        }
        throw new RuntimeException("Entrada no encontrada con ID: " + id);
    }

    public void deleteEntry(Long id) {
        Optional<Entry> optionalEntry = entryRepository.findById(id);
        if (optionalEntry.isPresent()) {
            Entry entry = optionalEntry.get();
            // Revertir el stock al eliminar la entrada
            if (entry.getInventory() != null && entry.getAmount() != null) {
                inventoryService.updateStock(entry.getInventory().getId(), -entry.getAmount());
            }
            entryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Entrada no encontrada con ID: " + id);
        }
    }

    public Optional<Entry> getEntryById(Long id) {
        return entryRepository.findById(id);
    }

    private void validateEntry(Entry entry) {
        if (entry.getInventory() == null) {
            throw new IllegalArgumentException("El inventario es requerido");
        }
        if (entry.getSupplier() == null) {
            throw new IllegalArgumentException("El proveedor es requerido");
        }
        if (entry.getEmployee() == null) {
            throw new IllegalArgumentException("El empleado es requerido");
        }
        if (entry.getAmount() == null || entry.getAmount() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        if (entry.getPriceBuy() == null || entry.getPriceBuy() <= 0) {
            throw new IllegalArgumentException("El precio de compra debe ser mayor a 0");
        }
        if (entry.getDateEntry() == null) {
            throw new IllegalArgumentException("La fecha de entrada es requerida");
        }
    }
}