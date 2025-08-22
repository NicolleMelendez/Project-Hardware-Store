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
    public String entryPage(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "entry/index";
    }

    @GetMapping("/api/entry")
    @ResponseBody
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }

    @PostMapping("/api/entry")
    @ResponseBody
    public Entry createEntry(@RequestBody Entry entry) {
        return entryService.createEntry(entry);
    }

    @PutMapping("/api/entry/{id}")
    @ResponseBody
    public Entry updateEntry(@PathVariable Long id, @RequestBody Entry entryDetails) {
        return entryService.updateEntry(id, entryDetails);
    }

    @DeleteMapping("/api/entry/{id}")
    @ResponseBody
    public void deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
    }

    @GetMapping("/api/entry/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable Long id) {
        return entryService.getEntryById(id).orElse(null);
    }
}

