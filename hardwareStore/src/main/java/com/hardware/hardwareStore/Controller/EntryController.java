package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
<<<<<<< HEAD
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Entry;
=======
>>>>>>> origin/nicolle
import com.hardware.hardwareStore.Service.EntryService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Entry;
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

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EmployeeService employeeService;



    @GetMapping
<<<<<<< HEAD
    public String showEntriesPage(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        model.addAttribute("inventories", inventoryService.getAllInventories());
=======
    public String entryPage(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        model.addAttribute("inventories", inventoryService.getAllInventory());
>>>>>>> origin/nicolle
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "entry/index";
    }

    @PostMapping("/save")
<<<<<<< HEAD
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
=======
    public String saveEntry(@ModelAttribute Entry entry) {
        entryService.createEntry(entry);
        return "redirect:/entry";
    }

    @DeleteMapping("/delete/{id}")
>>>>>>> origin/nicolle
    public String deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
        return "redirect:/entry";
    }

<<<<<<< HEAD
}
=======
    @GetMapping("/api/entry/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable Long id) {
        return entryService.getEntryById(id).orElse(null);
    }
}
>>>>>>> origin/nicolle
