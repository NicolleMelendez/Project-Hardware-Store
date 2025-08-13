package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.model.Supplier;
import com.hardware.hardwareStore.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        try {
            Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
            return ResponseEntity.ok(updatedSupplier);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}