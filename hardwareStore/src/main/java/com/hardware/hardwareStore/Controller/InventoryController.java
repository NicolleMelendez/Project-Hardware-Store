package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/inventory")
    public String listInventory(Model model) {
        model.addAttribute("inventoryList", inventoryService.findAll());
        model.addAttribute("inventory", new Inventory());
        model.addAttribute("suppliers", supplierService.findAll());
        return "inventory/index";
    }

    @PostMapping("/inventory/save")
    public String saveInventory(@ModelAttribute("inventory") Inventory inventory, RedirectAttributes redirectAttributes) {
        inventoryService.save(inventory);
        redirectAttributes.addFlashAttribute("success", "Producto guardado correctamente.");
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/delete/{id}")
    public String deleteInventory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        inventoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente.");
        return "redirect:/inventory";
    }


    @GetMapping("/api/inventories")
    @ResponseBody
    public List<Inventory> getAllInventoryApi() {
        return inventoryService.findAll();
    }

    @GetMapping("/api/inventories/{id}")
    @ResponseBody
    public ResponseEntity<Inventory> getInventoryByIdApi(@PathVariable Long id) {
        return inventoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/inventories")
    @ResponseBody
    public ResponseEntity<Inventory> createInventoryApi(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.save(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/api/inventories/{id}")
    @ResponseBody
    public ResponseEntity<Inventory> updateInventoryApi(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        return inventoryService.findById(id)
                .map(inventory -> {
                    inventory.setName(inventoryDetails.getName());
                    inventory.setUnit_weight(inventoryDetails.getUnit_weight());
                    inventory.setUnit_measure(inventoryDetails.getUnit_measure());
                    inventory.setCategory(inventoryDetails.getCategory());
                    inventory.setPrice(inventoryDetails.getPrice());
                    inventory.setStock(inventoryDetails.getStock());
                    inventory.setMinStock(inventoryDetails.getMinStock());
                    inventory.setSupplier(inventoryDetails.getSupplier());
                    Inventory updatedInventory = inventoryService.save(inventory);
                    return ResponseEntity.ok(updatedInventory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/inventories/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteInventoryApi(@PathVariable Long id) {
        if (!inventoryService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        inventoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}