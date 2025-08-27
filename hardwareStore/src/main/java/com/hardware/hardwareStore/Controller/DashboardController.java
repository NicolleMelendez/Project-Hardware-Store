package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String dashboardPage(Model model) {
        try {
            // Agrega logs para debug
            System.out.println("Daily sales: " + dashboardService.getDailySalesTotal());
            System.out.println("Top products: " + dashboardService.getTop5SoldProducts().size());
            System.out.println("Top customers: " + dashboardService.getTop5Customers().size());

            model.addAttribute("dailySales", dashboardService.getDailySalesTotal());
            model.addAttribute("weeklySales", dashboardService.getWeeklySalesTotal());
            model.addAttribute("monthlySales", dashboardService.getMonthlySalesTotal());
            model.addAttribute("topProducts", dashboardService.getTop5SoldProducts());
            model.addAttribute("lowStockProducts", dashboardService.getLowStockProducts());
            model.addAttribute("topCustomers", dashboardService.getTop5Customers());
            model.addAttribute("monthlySalesData", dashboardService.getMonthlySalesData());

        } catch (Exception e) {
            e.printStackTrace();
            // Maneja el error apropiadamente
        }
        return "index";
    }
}