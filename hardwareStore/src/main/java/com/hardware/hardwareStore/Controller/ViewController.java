package com.hardware.hardwareStore.Controller;



import org.springframework.ui.Model;
import com.hardware.hardwareStore.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/client")
    public String clientPage(Model model) {
        model.addAttribute("content", "client/index :: content");
        return "client/index";
    }

    @GetMapping("/employee")
    public String showEmployeePage() {
        return "employee/index";
    }

    @GetMapping("/entry")
    public String showEntryPage() {
        return "entry/index";
    }

    @GetMapping("/inventory")
    public String showInventoryPage() {
        return "inventory/index";
    }

    @GetMapping("/issue")
    public String showIssuePage() {
        return "issue/index";
    }

    @GetMapping("/orderBuy")
    public String showOrderBuyPage() {
        return "orderBuy/index";
    }

    @GetMapping("/sale")
    public String showSalePage() {
        return "sale/index";
    }

    @GetMapping("/supplier")
    public String showSupplierPage() {
        return "supplier/index";
    }





}
