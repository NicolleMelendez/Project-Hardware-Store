package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String inventoryPage(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "inventory/index";
    }

    @PostMapping("/save")
    public String saveInventory(@ModelAttribute Inventory inventory) {
        inventoryService.createInventory(inventory);
        return "redirect:/inventory";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventory";
    }
}
