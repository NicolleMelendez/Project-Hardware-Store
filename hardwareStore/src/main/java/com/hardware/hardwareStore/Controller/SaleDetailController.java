package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.model.SaleDetail;
import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-details")
public class SaleDetailController {

    @Autowired
    private SaleDetailRepository repository;

    @GetMapping
    public List<SaleDetail> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public SaleDetail create(@RequestBody SaleDetail detail) {
        return repository.save(detail);
    }

    @DeleteMapping
    public void delete(@RequestBody SaleDetail detail) {
        repository.delete(detail);
    }
}