package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.SaleService;
import com.hardware.hardwareStore.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired private SaleService saleService;
    @Autowired private ClientService clientService;
    @Autowired private EmployeeService employeeService;

    @GetMapping
    public String salePage(Model model) {
        model.addAttribute("sales", saleService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "sale/index";
    }

    @PostMapping("/save")
    public String saveSale(@ModelAttribute Sale sale, RedirectAttributes redirectAttributes) {
        try {
            saleService.save(sale);
            redirectAttributes.addFlashAttribute("success", "Venta creada exitosamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Error de integridad de datos: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la venta: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    @PostMapping("/update/status")
    public String updateSaleStatus(@RequestParam("id") Long id,
                                   @RequestParam("status") String status,
                                   RedirectAttributes redirectAttributes) {
        try {
            saleService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Estado actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Sale> getSaleByIdApi(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();
    }
}