package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.model.Entry;
import com.hardware.hardwareStore.Repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    @Autowired
    private EntryRepository repository;

    @GetMapping
    public List<Entry> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Entry getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Entry create(@RequestBody Entry entry) {
        return repository.save(entry);
    }

    @PutMapping("/{id}")
    public Entry update(@PathVariable Long id, @RequestBody Entry entry) {
        entry.setId(id);
        return repository.save(entry);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

