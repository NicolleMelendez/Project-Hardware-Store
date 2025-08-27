package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Issue;
import com.hardware.hardwareStore.Repository.IssueRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        // Inicializa las relaciones para evitar errores de carga perezosa (Lazy Loading)
        Hibernate.initialize(issue.getInventory());
        Hibernate.initialize(issue.getEmployee());
        return issue;
    }

    @Transactional
    public Issue createIssue(Issue issue) {
        validateRelations(issue);

        if (issue.getDateIssue() == null) {
            issue.setDateIssue(new Date());
        }

        // Descuenta el stock del producto
        inventoryService.updateStock(issue.getInventory().getId(), -issue.getAmount());

        return issueRepository.save(issue);
    }

    @Transactional
    public Issue updateIssue(Long id, Issue issueDetails) {
        Issue existingIssue = getIssueById(id);
        validateRelations(issueDetails);

        Long oldInventoryId = existingIssue.getInventory().getId();
        Long newInventoryId = issueDetails.getInventory().getId();
        int oldAmount = existingIssue.getAmount();
        int newAmount = issueDetails.getAmount();

        // Si el producto es el mismo, ajusta la diferencia de stock
        if (Objects.equals(oldInventoryId, newInventoryId)) {
            int amountDifference = newAmount - oldAmount;
            inventoryService.updateStock(oldInventoryId, -amountDifference);
        } else {
            // Si el producto cambió, revierte el stock del producto antiguo
            inventoryService.updateStock(oldInventoryId, oldAmount);
            // y descuenta el stock del nuevo producto
            inventoryService.updateStock(newInventoryId, -newAmount);
        }

        // Actualiza los campos de la salida
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
        // Al eliminar, restaura el stock del producto
        inventoryService.updateStock(issue.getInventory().getId(), issue.getAmount());
        issueRepository.delete(issue);
    }

    private void validateRelations(Issue issue) {
        if (issue.getInventory() == null || issue.getInventory().getId() == null) {
            throw new IllegalArgumentException("El producto es requerido.");
        }
        if (issue.getEmployee() == null || issue.getEmployee().getId() == null) {
            throw new IllegalArgumentException("El empleado es requerido.");
        }

        // Verifica que el producto y el empleado realmente existan en la BD
        inventoryService.findById(issue.getInventory().getId())
                .orElseThrow(() -> new RuntimeException("El producto seleccionado no existe."));
        employeeService.getEmployeeById(issue.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("El empleado seleccionado no existe."));
    }
}