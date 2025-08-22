package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.OrderBuyService;
import com.hardware.hardwareStore.model.OrderBuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderbuy")
public class OrderBuyController {

    @Autowired
    private OrderBuyService orderBuyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String orderBuyPage(Model model) {
        model.addAttribute("orders", orderBuyService.getAllOrders());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "orderbuy/index";
    }

    @PostMapping("/save")
    public String saveOrderBuy(@ModelAttribute OrderBuy orderBuy) {
        orderBuyService.createOrder(orderBuy);
        return "redirect:/orderbuy";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrderBuy(@PathVariable Long id) {
        orderBuyService.deleteOrder(id);
        return "redirect:/orderbuy";
    }

    @DeleteMapping("/api/orderBuy/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteOrderBuyApi(@PathVariable Long id) {
        orderBuyService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
