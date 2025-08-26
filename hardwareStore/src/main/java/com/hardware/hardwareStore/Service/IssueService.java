package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Issue;
import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.Repository.IssueRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private EmployeeService employeeService;

    @Transactional(readOnly = true)
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Issue getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida no encontrada con ID: " + id));
        Hibernate.initialize(issue.getInventory());
        Hibernate.initialize(issue.getEmployee());
        return issue;
    }

    @Transactional
    public Issue createIssue(Issue issue) {
        validateRelations(issue); // Valida que el empleado y el inventario existan

        // Asigna la fecha actual si no viene una
        if (issue.getDateIssue() == null) {
            issue.setDateIssue(new Date());
        }

        // Descuenta el stock
        inventoryService.updateStock(issue.getInventory().getId(), -issue.getAmount());

        return issueRepository.save(issue);
    }

    @Transactional
    public Issue updateIssue(Long id, Issue issueDetails) {
        Issue existingIssue = getIssueById(id);
        validateRelations(issueDetails);

        // Lógica de actualización de stock sencilla
        int amountDifference = issueDetails.getAmount() - existingIssue.getAmount();
        inventoryService.updateStock(existingIssue.getInventory().getId(), -amountDifference);

        // Actualiza los campos
        existingIssue.setInventory(issueDetails.getInventory());
        existingIssue.setAmount(issueDetails.getAmount());
        existingIssue.setReason(issueDetails.getReason());
        existingIssue.setDateIssue(issueDetails.getDateIssue());
        existingIssue.setEmployee(issueDetails.getEmployee());
        existingIssue.setObservation(issueDetails.getObservation());

        return issueRepository.save(existingIssue);
    }

    @Transactional
    public void deleteIssue(Long id) {
        Issue issue = getIssueById(id);
        // Restaura el stock al eliminar la salida
        inventoryService.updateStock(issue.getInventory().getId(), issue.getAmount());
        issueRepository.delete(issue);
    }

    // Método privado para validar que las relaciones existan
    private void validateRelations(Issue issue) {
        if (issue.getInventory() == null || issue.getInventory().getId() == null) {
            throw new IllegalArgumentException("El producto es requerido.");
        }
        if (issue.getEmployee() == null || issue.getEmployee().getId() == null) {
            throw new IllegalArgumentException("El empleado es requerido.");
        }

        // Verifica que el producto exista en la BD
        inventoryService.findById(issue.getInventory().getId())
                .orElseThrow(() -> new RuntimeException("El producto seleccionado no existe."));

        // Verifica que el empleado exista en la BD
        employeeService.getEmployeeById(issue.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("El empleado seleccionado no existe."));
    }
}