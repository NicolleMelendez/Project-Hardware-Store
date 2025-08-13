package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.Entry;
import com.hardware.hardwareStore.Service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @GetMapping
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable Long id) {
        Entry entry = entryService.getEntryById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));
        return ResponseEntity.ok(entry);
    }

    @PostMapping
    public ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {
        Entry savedEntry = entryService.createEntry(entry);
        return ResponseEntity.ok(savedEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody Entry entry) {
        Entry updatedEntry = entryService.updateEntry(id, entry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inventory/{inventoryId}")
    public List<Entry> getEntriesByInventory(@PathVariable Long inventoryId) {
        return entryService.getEntriesByInventory(inventoryId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<Entry> getEntriesBySupplier(@PathVariable Long supplierId) {
        return entryService.getEntriesBySupplier(supplierId);
    }

    @GetMapping("/date-range")
    public List<Entry> getEntriesBetweenDates(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return entryService.getEntriesBetweenDates(start, end);
    }
}