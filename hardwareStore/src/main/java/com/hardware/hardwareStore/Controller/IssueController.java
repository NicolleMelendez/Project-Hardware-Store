package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.IssueService;
import com.hardware.hardwareStore.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String issuePage(Model model) {
        model.addAttribute("issues", issueService.getAllIssues());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "issue/index";
    }

    @GetMapping("/api/issue")
    @ResponseBody
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @PostMapping("/api/issue")
    @ResponseBody
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.createIssue(issue);
    }

    @PutMapping("/api/issue/{id}")
    @ResponseBody
    public Issue updateIssue(@PathVariable Long id, @RequestBody Issue issueDetails) {
        return issueService.updateIssue(id, issueDetails);
    }

    @DeleteMapping("/api/issue/{id}")
    @ResponseBody
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }

    @GetMapping("/api/issue/{id}")
    @ResponseBody
    public Issue getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }
}
