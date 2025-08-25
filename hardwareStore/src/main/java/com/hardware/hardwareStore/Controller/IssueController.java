package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.IssueService;
import com.hardware.hardwareStore.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private EmployeeService employeeService;

    // Formateador de fechas para el binding
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping
    public String issuePage(Model model) {
        model.addAttribute("issues", issueService.getAllIssues());
        model.addAttribute("inventories", inventoryService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("issue", new Issue());
        return "issue/index";
    }

    @PostMapping("/save")
    public String saveIssue(@ModelAttribute Issue issue, RedirectAttributes redirectAttributes) {
        try {
            // Si la fecha es null, establecer fecha actual
            if (issue.getDateIssue() == null) {
                issue.setDateIssue(new Date());
            }
            issueService.createIssue(issue);
            redirectAttributes.addFlashAttribute("success", "Salida creada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/issue";
    }

    @PostMapping("/update")
    public String updateIssue(@ModelAttribute Issue issue, RedirectAttributes redirectAttributes) {
        try {
            issueService.updateIssue(issue.getId(), issue);
            redirectAttributes.addFlashAttribute("success", "Salida actualizada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/issue";
    }

    @PostMapping("/delete/{id}")
    public String deleteIssue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            issueService.deleteIssue(id);
            redirectAttributes.addFlashAttribute("success", "Salida eliminada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/issue";
    }

    // ========== API REST ==========

    @GetMapping("/api/issue")
    @ResponseBody
    public ResponseEntity<List<Issue>> getAllIssuesApi() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> getIssueByIdApi(@PathVariable Long id) {
        try {
            Issue issue = issueService.getIssueById(id);
            return ResponseEntity.ok(issue);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}