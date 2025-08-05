package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.model.OrderDetail;
import com.hardware.hardwareStore.Repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository repository;

    @GetMapping
    public List<OrderDetail> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public OrderDetail create(@RequestBody OrderDetail detail) {
        return repository.save(detail);
    }

    @DeleteMapping
    public void delete(@RequestBody OrderDetail detail) {
        repository.delete(detail);
    }
}
