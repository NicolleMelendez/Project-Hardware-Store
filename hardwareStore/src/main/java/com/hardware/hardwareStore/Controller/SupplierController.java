package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // ==================================================
    // Métodos para la VISTA (Thymeleaf)
    // ==================================================

    @GetMapping("/supplier")
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        // ↓↓↓ ESTA LÍNEA FALTABA Y CAUSABA EL ERROR ↓↓↓
        model.addAttribute("supplier", new Supplier());
        return "supplier/index";
    }

    @PostMapping("/supplier/save")
    public String saveSupplier(@ModelAttribute Supplier supplier, RedirectAttributes redirectAttributes) {
        try {
            if (supplier.getId() == null) {
                supplierService.createSupplier(supplier);
                redirectAttributes.addFlashAttribute("success", "Proveedor creado exitosamente.");
            } else {
                supplierService.updateSupplier(supplier.getId(), supplier);
                redirectAttributes.addFlashAttribute("success", "Proveedor actualizado exitosamente.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el proveedor: " + e.getMessage());
        }
        return "redirect:/supplier";
    }

    @PostMapping("/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            supplierService.deleteSupplier(id);
            redirectAttributes.addFlashAttribute("success", "Proveedor eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el proveedor.");
        }
        return "redirect:/supplier";
    }

    // ==================================================
    // Métodos para la API (devuelven JSON)
    // ==================================================

    @GetMapping("/api/suppliers")
    @ResponseBody
    public List<Supplier> getAllSuppliersApi() {
        return supplierService.findAll();
    }

    @GetMapping("/api/suppliers/{id}")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplierByIdApi(@PathVariable Long id) {
        Supplier supplier = supplierService.findById(id);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}