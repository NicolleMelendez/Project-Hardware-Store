package com.hardware.hardwareStore.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class DashboardController {


    @GetMapping
    public String dashboardPage(Model model) {
        // --- Añadimos todos los datos para los reportes al modelo ---

        // Ventas
        model.addAttribute("dailySales", dashboardService.getDailySalesTotal());
        model.addAttribute("weeklySales", dashboardService.getWeeklySalesTotal());
        model.addAttribute("monthlySales", dashboardService.getMonthlySalesTotal());

        // Productos
        model.addAttribute("topProducts", dashboardService.getTop5SoldProducts());
        model.addAttribute("lowStockProducts", dashboardService.getLowStockProducts());

        // Personas
        model.addAttribute("topCustomers", dashboardService.getTop5Customers());
        model.addAttribute("topEmployees", dashboardService.getTop5Employees());

        return "dashboard/index";
    }
}
