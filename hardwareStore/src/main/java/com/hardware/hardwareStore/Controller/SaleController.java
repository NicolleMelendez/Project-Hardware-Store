package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final InventoryService inventoryService;

    public SaleController(SaleService saleService,
                          ClientService clientService,
                          EmployeeService employeeService,
                          InventoryService inventoryService) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String listSales(Model model) {
        try {
            List<Sale> sales = saleService.getAllSales();
            List<Client> clients = clientService.findAll();
            List<Employee> employees = employeeService.getAllEmployees();
            List<Inventory> allInventory = inventoryService.findAll();

            model.addAttribute("sales", sales);
            model.addAttribute("clients", clients);
            model.addAttribute("employees", employees);
            model.addAttribute("allInventory", allInventory);
            model.addAttribute("sale", new Sale()); // Para el formulario

            return "sale/index";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar ventas: " + e.getMessage());
            return "sale/index";
        }
    }

    @PostMapping("/save")
    public String saveSale(@ModelAttribute Sale sale,
                           @RequestParam("productIds") List<Long> productIds,
                           @RequestParam("quantities") List<Integer> quantities,
                           @RequestParam("prices") List<Integer> prices,
                           RedirectAttributes redirectAttributes) {
        try {
            Sale savedSale = saleService.saveSaleWithDetails(sale, productIds, quantities, prices);
            redirectAttributes.addFlashAttribute("success", "Venta guardada exitosamente. Total: $" + savedSale.getTotal());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la venta: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    @PostMapping("/update/status")
    public String updateSaleStatus(@RequestParam Long id,
                                   @RequestParam String status,
                                   RedirectAttributes redirectAttributes) {
        try {
            Sale updatedSale = saleService.updateSaleStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Estado actualizado a: " + updatedSale.getStatus());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar estado: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Sale getSaleApi(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @GetMapping("/api/{id}/details")
    @ResponseBody
    public List<SaleDetail> getSaleDetailsApi(@PathVariable Long id) {
        return saleService.getSaleDetails(id);
    }

    @ModelAttribute("statusOptions")
    public List<String> getStatusOptions() {
        return Arrays.asList("PENDIENTE", "COMPLETADA", "CANCELADA");
    }
}