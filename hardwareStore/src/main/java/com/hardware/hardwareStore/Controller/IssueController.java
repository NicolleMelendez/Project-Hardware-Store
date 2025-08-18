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

    @PostMapping("/save")
    public String saveIssue(@ModelAttribute Issue issue) {
        issueService.createIssue(issue);
        return "redirect:/issue";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return "redirect:/issue";
    }

    @GetMapping("/api/issue/{id}")
    @ResponseBody
    public Issue getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }
}
