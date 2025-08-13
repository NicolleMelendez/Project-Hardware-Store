package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.Sale;
import com.hardware.hardwareStore.model.Client;
import com.hardware.hardwareStore.model.Employee;
import com.hardware.hardwareStore.Repository.SaleRepository;
import com.hardware.hardwareStore.Repository.ClientRepository;
import com.hardware.hardwareStore.Repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sales") // Base path para todas las rutas
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Vista principal de ventas
    @GetMapping
    public String salePage(Model model) {
        if (!model.containsAttribute("sale")) {
            model.addAttribute("sale", new Sale());
        }
        model.addAttribute("sales", saleRepository.findAllByOrderByDateSaleDesc());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "sale/index";
    }

    @PostMapping("/save")
    public String saveSale(@Valid @ModelAttribute("sale") Sale sale,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Agrega los errores y el objeto al modelo para redirigir
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sale", bindingResult);
            redirectAttributes.addFlashAttribute("sale", sale);
            return "redirect:/sales";
        }

        try {
            // Lógica de guardado
            saleRepository.save(sale);
            redirectAttributes.addFlashAttribute("success", "Venta guardada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("sale", sale);
        }

        return "redirect:/sales";
    }


    // Eliminar venta (con validación)
    @PostMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Sale sale = saleRepository.findById(id).orElse(null);
            if (sale == null) {
                redirectAttributes.addFlashAttribute("error", "La venta no existe");
                return "redirect:/sales";
            }

            // Validar si la venta puede ser eliminada (ejemplo: solo ventas PENDIENTES)
            if ("COMPLETADA".equals(sale.getStatus())) {
                throw new IllegalStateException("No se pueden eliminar ventas COMPLETADAS");
            }

            saleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Venta eliminada exitosamente");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la venta: " + e.getMessage());
        }

        return "redirect:/sales";
    }
}