package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.model.Issue;
import com.hardware.hardwareStore.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(issueService.getIssueById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
        Issue createdIssue = issueService.createIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIssue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue issue) {
        try {
            Issue updatedIssue = issueService.updateIssue(id, issue);
            return ResponseEntity.ok(updatedIssue);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        try {
            issueService.deleteIssue(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/inventory/{inventoryId}")
    public List<Issue> getIssuesByInventory(@PathVariable Long inventoryId) {
        return issueService.getIssuesByInventory(inventoryId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Issue> getIssuesByEmployee(@PathVariable Long employeeId) {
        return issueService.getIssuesByEmployee(employeeId);
    }

    @GetMapping("/date-range")
    public List<Issue> getIssuesBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return issueService.getIssuesBetweenDates(start, end);
    }

    @GetMapping("/search")
    public List<Issue> searchIssuesByReason(@RequestParam String reason) {
        return issueService.searchIssuesByReason(reason);
    }
}