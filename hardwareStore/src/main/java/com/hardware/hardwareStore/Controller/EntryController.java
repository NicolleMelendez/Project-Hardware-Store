package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SupplierService;
import com.hardware.hardwareStore.model.Entry;
import com.hardware.hardwareStore.Service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

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
        model.addAttribute("inventories", inventoryService.findAll());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("entry", new Entry()); // Objeto para el formulario
        return "entry/index";
    }

    @PostMapping("/save")
    public String saveEntry(@ModelAttribute Entry entry, RedirectAttributes redirectAttributes) {
        try {
            // Asegurar que la fecha de entrada tenga valor si es nula
            if (entry.getDateEntry() == null) {
                entry.setDateEntry(new Date());
            }

            entryService.createEntry(entry);
            redirectAttributes.addFlashAttribute("success", "Entrada creada exitosamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Datos inválidos: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Error de integridad de datos: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la entrada: " + e.getMessage());
        }
        return "redirect:/entry";
    }

    @PostMapping("/update/{id}")
    public String updateEntry(@PathVariable Long id, @ModelAttribute Entry entry, RedirectAttributes redirectAttributes) {
        try {
            entryService.updateEntry(id, entry);
            redirectAttributes.addFlashAttribute("success", "Entrada actualizada exitosamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Datos inválidos: " + e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la entrada: " + e.getMessage());
        }
        return "redirect:/entry";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            entryService.deleteEntry(id);
            redirectAttributes.addFlashAttribute("success", "Entrada eliminada exitosamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la entrada: " + e.getMessage());
        }
        return "redirect:/entry";
    }

    // API endpoints para AJAX - MEJORADOS
    @GetMapping("/api/entry")
    @ResponseBody
    public ResponseEntity<List<Entry>> getAllEntriesApi() {
        try {
            return ResponseEntity.ok(entryService.getAllEntries());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/entry")
    @ResponseBody
    public ResponseEntity<?> createEntryApi(@RequestBody Entry entry) {
        try {
            if (entry.getDateEntry() == null) {
                entry.setDateEntry(new Date());
            }
            Entry newEntry = entryService.createEntry(entry);
            return ResponseEntity.ok(newEntry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PutMapping("/api/entry/{id}")
    @ResponseBody
    public ResponseEntity<?> updateEntryApi(@PathVariable Long id, @RequestBody Entry entryDetails) {
        try {
            Entry updatedEntry = entryService.updateEntry(id, entryDetails);
            return ResponseEntity.ok(updatedEntry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @DeleteMapping("/api/entry/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteEntryApi(@PathVariable Long id) {
        try {
            entryService.deleteEntry(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @GetMapping("/api/entry/{id}")
    @ResponseBody
    public ResponseEntity<Entry> getEntryByIdApi(@PathVariable Long id) {
        try {
            return entryService.getEntryById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}