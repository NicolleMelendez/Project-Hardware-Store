package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.model.OrderBuy;
import com.hardware.hardwareStore.Repository.OrderBuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderBuyController {

    @Autowired
    private OrderBuyRepository repository;

    @GetMapping
    public List<OrderBuy> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public OrderBuy getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public OrderBuy create(@RequestBody OrderBuy order) {
        return repository.save(order);
    }

    @PutMapping("/{id}")
    public OrderBuy update(@PathVariable Long id, @RequestBody OrderBuy order) {
        order.setId(id);
        return repository.save(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
