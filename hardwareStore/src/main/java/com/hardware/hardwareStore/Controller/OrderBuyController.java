package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.OrderBuy;
import com.hardware.hardwareStore.Service.OrderBuyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderBuyController {

    private final OrderBuyService orderBuyService;

    public OrderBuyController(OrderBuyService orderBuyService) {
        this.orderBuyService = orderBuyService;
    }

    @GetMapping
    public ResponseEntity<List<OrderBuy>> getAllOrders() {
        return ResponseEntity.ok(orderBuyService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBuy> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderBuyService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderBuy> createOrder(@RequestBody OrderBuy orderBuy) {
        return ResponseEntity.ok(orderBuyService.createOrder(orderBuy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderBuy> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderBuy orderBuy) {
        return ResponseEntity.ok(orderBuyService.updateOrder(id, orderBuy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderBuyService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}