package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String dashboardPage(Model model) {
        //model.addAttribute("topProducts", dashboardService.getTop5SoldProducts());
        model.addAttribute("topCustomers", dashboardService.getTop5Customers());
        model.addAttribute("topEmployees", dashboardService.getTop5Employees());
        return "dashboard/index";
    }
}
