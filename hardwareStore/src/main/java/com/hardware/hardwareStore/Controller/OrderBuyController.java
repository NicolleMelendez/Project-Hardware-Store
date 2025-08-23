package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.OrderBuyService;
import com.hardware.hardwareStore.model.OrderBuy;
import com.hardware.hardwareStore.Exception.OrderBuyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orderbuy")
public class OrderBuyController {

    @Autowired
    private OrderBuyService orderBuyService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    /*
     * Mapeo normal para Thymeleaf
     */
    @GetMapping()
    public String orderBuyPage(Model model) {
        model.addAttribute("orders", orderBuyService.getAllOrders());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("orderBuy", new OrderBuy());
        return "orderbuy/index";
    }

    @PostMapping("/save")
    public String saveOrderBuy(@ModelAttribute OrderBuy orderBuy, RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.createOrder(orderBuy);
            redirectAttributes.addFlashAttribute("success", "Orden comprada creada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la orden: " + e.getMessage());
        }
        return "redirect:/orderbuy";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderBuy(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("success", "Orden eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la orden: " + e.getMessage());
        }
        return "redirect:/orderbuy";
    }

    @GetMapping("/edit/{id}")
    public String editOrderBuyPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            OrderBuy orderBuy = orderBuyService.getOrderById(id);
            model.addAttribute("orderBuy", orderBuy);
            model.addAttribute("orders", orderBuyService.getAllOrders());
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "orderbuy/index";
        } catch (OrderBuyNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Orden no encontrada");
            return "redirect:/orderbuy";
        }
    }

    /*
     * Mapeo para las APIs (igual que Client)
     */
    @GetMapping("/api/orderBuy")
    @ResponseBody
    public List<OrderBuy> getAllOrderBuys() {
        return orderBuyService.getAllOrders();
    }

    @GetMapping("/api/orderBuy/{id}")
    @ResponseBody
    public ResponseEntity<OrderBuy> getOrderBuyById(@PathVariable Long id) {
        try {
            OrderBuy orderBuy = orderBuyService.getOrderById(id);
            return ResponseEntity.ok(orderBuy);
        } catch (OrderBuyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/orderBuy")
    @ResponseBody
    public ResponseEntity<OrderBuy> createOrderBuy(@RequestBody OrderBuy orderBuy) {
        OrderBuy createdOrder = orderBuyService.createOrder(orderBuy);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/api/orderBuy/{id}")
    @ResponseBody
    public ResponseEntity<OrderBuy> updateOrderBuy(@PathVariable Long id, @RequestBody OrderBuy orderBuy) {
        try {
            OrderBuy updatedOrder = orderBuyService.updateOrder(id, orderBuy);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderBuyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/orderBuy/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteOrderBuyApi(@PathVariable Long id) {
        try {
            orderBuyService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (OrderBuyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}