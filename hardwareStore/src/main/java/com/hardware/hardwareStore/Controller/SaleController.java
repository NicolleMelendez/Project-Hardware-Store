package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.SaleService;
import com.hardware.hardwareStore.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String salePage(Model model) {
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
    }
}
