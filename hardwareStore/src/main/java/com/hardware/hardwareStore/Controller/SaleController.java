package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.SaleService;
import com.hardware.hardwareStore.model.Sale;
<<<<<<< HEAD
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
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
>>>>>>> origin/nicolle
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

<<<<<<< HEAD

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sales") // Base path para todas las rutas
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;
=======
import java.util.Date;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;
>>>>>>> origin/nicolle

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Vista principal de ventas
    @GetMapping
    public String salePage(Model model) {
<<<<<<< HEAD
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
=======
        model.addAttribute("sales", saleService.getAllSales());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "sale/index";
    }

    @GetMapping("/api/sale/{id}")
    @ResponseBody
    public Sale getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping("/save")
    public String saveSale(@ModelAttribute Sale sale) {
        saleService.createSale(sale);
        return "redirect:/sale";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return "redirect:/sale";
>>>>>>> origin/nicolle
    }
}