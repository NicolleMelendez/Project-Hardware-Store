package com.hardware.hardwareStore.Service;
import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final InventoryService inventoryService;
    private final EmployeeService employeeService;

    @Autowired
    public IssueService(IssueRepository issueRepository,
                        InventoryService inventoryService,
                        EmployeeService employeeService) {
        this.issueRepository = issueRepository;
        this.inventoryService = inventoryService;
        this.employeeService = employeeService;
    }

    @Transactional(readOnly = true)
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la salida: " + id));
    }

    @Transactional
    public Issue createIssue(Issue issue) {
        validateRelations(issue);
        updateInventoryStock(issue.getInventory(), - issue.getAmount()); // Reduce stock
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue updateIssue(Long id, Issue issueDetails) {
        Issue issue = getIssueById(id);

        // Calculate stock difference
        int amountDifference = issue.getAmount() - issueDetails.getAmount();
        updateInventoryStock(issueDetails.getInventory(), amountDifference);

        validateRelations(issueDetails);

        issue.setInventory(issueDetails.getInventory());
        issue.setAmount(issueDetails.getAmount());
        issue.setReason(issueDetails.getReason());
        issue.setDateIssue(issueDetails.getDateIssue());
        issue.setEmployee(issueDetails.getEmployee());
        issue.setObservation(issueDetails.getObservation());

        return issueRepository.save(issue);
    }

    @Transactional
    public void deleteIssue(Long id) {
        Issue issue = getIssueById(id);
        // Restore stock when deleting an issue
        updateInventoryStock(issue.getInventory(), issue.getAmount());
        issueRepository.delete(issue);
    }

    @Transactional(readOnly = true)
    public List<Issue> getIssuesByInventory(Long inventoryId) {
        return issueRepository.findByInventoryId(inventoryId);
    }

    @Transactional(readOnly = true)
    public List<Issue> getIssuesByEmployee(Long employeeId) {
        return issueRepository.findByEmployeeId(employeeId);
    }

    @Transactional(readOnly = true)
    public List<Issue> getIssuesBetweenDates(Date start, Date end) {
        return issueRepository.findByDateIssueBetween(start, end);
    }

    @Transactional(readOnly = true)
    public List<Issue> searchIssuesByReason(String reason) {
        return issueRepository.findByReasonContainingIgnoreCase(reason);
    }

    private void validateRelations(Issue issue) {
        if (!inventoryService.findById(issue.getInventory().getId()).isPresent()) {
            throw new RuntimeException("No se encontro el producto");
        }

        if (!employeeService.getEmployeeById(issue.getEmployee().getId()).isPresent()) {
            throw new RuntimeException("No se encontro el empleado");
        }
    }

    private void updateInventoryStock(Inventory inventory, int amount) {
        inventoryService.updateStock(inventory.getId(), amount);
    }
}