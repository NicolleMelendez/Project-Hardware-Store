package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Repository.InventoryRepository;
import com.hardware.hardwareStore.Repository.OrderBuyRepository;
import com.hardware.hardwareStore.Repository.OrderDetailRepository;
import com.hardware.hardwareStore.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderBuyRepository orderBuyRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public String orderDetailPage(Model model) {
        model.addAttribute("details", orderDetailRepository.findAll());
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        return "orderdetail/index";
    }

    @PostMapping("/save")
    public String saveOrderDetail(@ModelAttribute OrderDetail detail) {
        orderDetailRepository.save(detail);
        return "redirect:/orderdetail";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable Long id) {
        orderDetailRepository.deleteById(id);
        return "redirect:/orderdetail";
    }
}
