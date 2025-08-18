package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;

import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Entry;
import com.hardware.hardwareStore.Service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/entry")
public class EntryController {

    @Autowired
    private EntryService entryService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public String showEntriesPage(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "entry/index";
    }


    public String entryPage(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "entry/index";
    }

    @PostMapping("/save")
    public String createEntry(@ModelAttribute Entry entry) {
        entryService.createEntry(entry);
        return "redirect:/entry"; // Actualizado para coincidir con el RequestMapping
    }


    @PostMapping("/update/{id}")
    public String updateEntry(@PathVariable Long id, @ModelAttribute Entry entryDetails) {
        entryService.updateEntry(id, entryDetails);
        return "redirect:/entry";
    }

    @GetMapping("/delete/{id}")
    public String saveEntry(@ModelAttribute Entry entry) {
        entryService.createEntry(entry);
        return "redirect:/entry";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
        return "redirect:/entry";
    }

    @GetMapping("/api/entry/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable Long id) {
        return entryService.getEntryById(id).orElse(null);
    }
}

