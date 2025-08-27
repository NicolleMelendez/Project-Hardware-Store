package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.IssueService;
import com.hardware.hardwareStore.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller // No se usa @RequestMapping a nivel de clase
public class IssueController {

    @Autowired
    private IssueService issueService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private EmployeeService employeeService;

    // ==================================================
    // Métodos para la VISTA (Thymeleaf)
    // ==================================================

    @GetMapping("/issue") // Ruta completa para la vista
    public String issuePage(Model model) {
        model.addAttribute("issues", issueService.getAllIssues());
        model.addAttribute("inventories", inventoryService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("issue", new Issue());
        return "issue/index";
    }

    @PostMapping("/issue/save") // Ruta completa
    public String saveIssue(@ModelAttribute Issue issue, RedirectAttributes redirectAttributes) {
        try {
            if (issue.getId() == null) {
                issueService.createIssue(issue);
                redirectAttributes.addFlashAttribute("success", "Salida creada exitosamente.");
            } else {
                issueService.updateIssue(issue.getId(), issue);
                redirectAttributes.addFlashAttribute("success", "Salida actualizada exitosamente.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/issue";
    }

    @PostMapping("/issue/delete/{id}") // Ruta completa
    public String deleteIssue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            issueService.deleteIssue(id);
            redirectAttributes.addFlashAttribute("success", "Salida eliminada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/issue";
    }

    // ==================================================
    // Métodos para la API (devuelven JSON)
    // ==================================================

    @GetMapping("/api/issues") // <-- ¡URL deseada para obtener todo!
    @ResponseBody
    public List<Issue> getAllIssuesApi() {
        return issueService.getAllIssues();
    }

    @GetMapping("/api/issues/{id}") // URL para obtener uno por ID
    @ResponseBody
    public ResponseEntity<Issue> getIssueByIdApi(@PathVariable Long id) {
        try {
            Issue issue = issueService.getIssueById(id);
            return ResponseEntity.ok(issue);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}