package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.OrderBuyService;
import com.hardware.hardwareStore.model.OrderBuy;
import com.hardware.hardwareStore.model.OrderDetail;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order-buy")
@AllArgsConstructor
public class OrderBuyController {

    private final OrderBuyService orderBuyService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final InventoryService inventoryService;

    @GetMapping
    public String orderBuyPage(Model model) {
        model.addAttribute("orders", orderBuyService.getAllOrders());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("allInventory", inventoryService.findAll());
        return "orderBuy/index";
    }

    @PostMapping
    public String saveOrderBuy(@ModelAttribute OrderBuy orderBuy,
                               @RequestParam("productIds") List<Long> productIds,
                               @RequestParam("quantities") List<Integer> quantities,
                               @RequestParam("prices") List<Integer> prices,
                               RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.saveOrderBuyWithDetails(orderBuy, productIds, quantities, prices);
            redirectAttributes.addFlashAttribute("success", "Pedido guardado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el pedido: " + e.getMessage());
        }
        return "redirect:/order-buy";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("id") Long id,
                                    @RequestParam("status") String status,
                                    RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Estado actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
        }
        return "redirect:/order-buy";
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success", "Pedido cancelado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cancelar el pedido: " + e.getMessage());
        }
        return "redirect:/order-buy";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<OrderBuy> getOrderBuyById(@PathVariable Long id) {
        OrderBuy order = orderBuyService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/api/{id}/details")
    @ResponseBody
    public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable Long id) {
        List<OrderDetail> details = orderBuyService.getOrderDetails(id);
        return ResponseEntity.ok(details);
    }
}