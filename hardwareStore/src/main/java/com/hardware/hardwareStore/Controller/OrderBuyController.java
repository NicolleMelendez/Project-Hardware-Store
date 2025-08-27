package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderBuyController {
    private final OrderBuyService orderBuyService;
    private final SupplierService supplierService;
    private final EmployeeService employeeService;
    private final InventoryService inventoryService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderBuyService.getAllOrders());
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("allInventory", inventoryService.findAll());
        model.addAttribute("order", new OrderBuy());
        model.addAttribute("statusOptions", Arrays.asList(OrderStatus.values()));
        return "orderBuy/index";
    }

    @PostMapping
    public String saveOrder(@ModelAttribute OrderBuy order,
                            @RequestParam("productIds") List<Long> productIds,
                            @RequestParam("quantities") List<Integer> quantities,
                            @RequestParam("prices") List<Integer> prices,
                            RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.saveOrderWithDetails(order, productIds, quantities, prices);
            redirectAttributes.addFlashAttribute("success", "Orden de compra guardada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la orden: " + e.getMessage());
        }
        return "redirect:/orders";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam Long id,
                                    @RequestParam OrderStatus status,
                                    RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.updateOrderStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Estado actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar estado: " + e.getMessage());
        }
        return "redirect:/orders";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<OrderBuy> getOrderApi(@PathVariable Long id) {
        return ResponseEntity.ok(orderBuyService.getOrderById(id));
    }

    @GetMapping("/api/{id}/details")
    @ResponseBody
    public ResponseEntity<List<OrderDetail>> getOrderDetailsApi(@PathVariable Long id) {
        return ResponseEntity.ok(orderBuyService.getOrderDetails(id));
    }

    @ModelAttribute("statusOptions")
    public List<OrderStatus> getStatusOptions() {
        return Arrays.asList(OrderStatus.values());
    }
}