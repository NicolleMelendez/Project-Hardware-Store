package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier")
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        return "supplier/index"; // Carga templates/supplier/index.html
    }

    @PostMapping("/supplier/save")
    public String saveSupplier(@ModelAttribute Supplier supplier) {
        if (supplier.getId() == null) {
            supplierService.createSupplier(supplier);
        } else {
            supplierService.updateSupplier(supplier.getId(), supplier);
        }
        return "redirect:/supplier";
    }

    @PostMapping("/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/supplier";
    }

    /*
     * Mapeo para las apis
     */
    @GetMapping("/api/suppliers")
    @ResponseBody
    public List<Supplier> getAllSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/api/suppliers/{id}")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @PostMapping("/api/suppliers")
    @ResponseBody
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.createSupplier(supplier));
    }

    @PutMapping("/api/suppliers/{id}")
    @ResponseBody
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplier));
    }

    @DeleteMapping("/api/suppliers/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteSupplierApi(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}