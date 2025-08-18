package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Repository.SaleRepository;
import com.hardware.hardwareStore.Repository.ClientRepository;
import com.hardware.hardwareStore.Repository.EmployeeRepository;
import com.hardware.hardwareStore.Service.SaleService;
import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.model.Sale;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public SaleController(SaleService saleService,
                          ClientService clientService,
                          EmployeeService employeeService,
                          SaleRepository saleRepository,
                          ClientRepository clientRepository,
                          EmployeeRepository employeeRepository) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    // Vista principal de ventas
    @GetMapping
    public String salePage(Model model) {
        if (!model.containsAttribute("sale")) {
            model.addAttribute("sale", new Sale());
        }
        model.addAttribute("sales", saleService.getAllSale());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "sale/index";
    }

    // Guardar venta
    @PostMapping("/save")
    public String saveSale(@Valid @ModelAttribute("sale") Sale sale,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sale", bindingResult);
            redirectAttributes.addFlashAttribute("sale", sale);
            return "redirect:/sales";
        }

        try {
            saleService.save(sale);
            redirectAttributes.addFlashAttribute("success", "Venta guardada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("sale", sale);
        }

        return "redirect:/sales";
    }

    // Eliminar venta
    @PostMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Sale sale = saleRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));

            if ("COMPLETADA".equals(sale.getStatus())) {
                throw new IllegalStateException("No se pueden eliminar ventas COMPLETADAS");
            }

            saleService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Venta eliminada exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la venta: " + e.getMessage());
        }

        return "redirect:/sales";
    }

    // API para obtener venta por ID
    @GetMapping("/api/{id}")
    @ResponseBody
    public Sale getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }
}