package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.OrderBuyService;
import com.hardware.hardwareStore.model.OrderBuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveOrderBuy(@ModelAttribute OrderBuy orderBuy, RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.createOrder(orderBuy);
            redirectAttributes.addFlashAttribute("success", "Pedido creado exitosamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Error de integridad de datos: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el pedido: " + e.getMessage());
        }
        return "redirect:/orderbuy";
    }

    @PostMapping("/update/status")
    public String updateOrderStatus(@RequestParam("id") Long id,
                                    @RequestParam("status") String status,
                                    RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Estado actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
        }
        return "redirect:/orderbuy";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            orderBuyService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("success", "Pedido eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el pedido: " + e.getMessage());
        }
        return "redirect:/orderbuy";
    }

    @GetMapping("/api/orderBuy/{id}")
    @ResponseBody
    public ResponseEntity<OrderBuy> getOrderBuyById(@PathVariable Long id) {
        OrderBuy order = orderBuyService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }
}