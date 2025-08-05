package com.hardware.hardwareStore.Controller;


import com.hardware.hardwareStore.model.Issue;
import com.hardware.hardwareStore.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueRepository repository;

    @GetMapping
    public List<Issue> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Issue getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Issue create(@RequestBody Issue issue) {
        return repository.save(issue);
    }

    @PutMapping("/{id}")
    public Issue update(@PathVariable Long id, @RequestBody Issue issue) {
        issue.setId(id);
        return repository.save(issue);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

