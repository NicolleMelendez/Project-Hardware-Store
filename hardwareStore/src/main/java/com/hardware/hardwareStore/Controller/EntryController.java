package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.EntryService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller // No se usa @RequestMapping a nivel de clase
public class EntryController {

    @Autowired
    private EntryService entryService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private EmployeeService employeeService;

    // ==================================================
    // Métodos para la VISTA (Thymeleaf)
    // ==================================================

    @GetMapping("/entry") // Ruta completa para la vista
    public String entryPage(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        model.addAttribute("inventories", inventoryService.findAll());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("entry", new Entry());
        return "entry/index";
    }

    @PostMapping("/entry/save") // Ruta completa
    public String saveEntry(@ModelAttribute Entry entry, RedirectAttributes redirectAttributes) {
        try {
            if (entry.getDateEntry() == null) {
                entry.setDateEntry(new Date());
            }
            if (entry.getId() == null) {
                entryService.createEntry(entry);
                redirectAttributes.addFlashAttribute("success", "Entrada creada exitosamente.");
            } else {
                entryService.updateEntry(entry.getId(), entry);
                redirectAttributes.addFlashAttribute("success", "Entrada actualizada exitosamente.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/entry";
    }

    @PostMapping("/entry/delete/{id}") // Ruta completa
    public String deleteEntry(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            entryService.deleteEntry(id);
            redirectAttributes.addFlashAttribute("success", "Entrada eliminada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/entry";
    }

    // ==================================================
    // Métodos para la API (devuelven JSON)
    // ==================================================

    @GetMapping("/api/entries") // <-- ¡URL deseada para obtener todo!
    @ResponseBody
    public List<Entry> getAllEntriesApi() {
        return entryService.getAllEntries();
    }

    @GetMapping("/api/entries/{id}") // URL para obtener uno por ID
    @ResponseBody
    public ResponseEntity<Entry> getEntryByIdApi(@PathVariable Long id) {
        Optional<Entry> entry = entryService.getEntryById(id);
        return entry.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}