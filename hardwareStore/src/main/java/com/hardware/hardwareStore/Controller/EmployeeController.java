package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/index";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute Employee employee, Model model) {
        try {
            if (employee.getId() == null || employee.getId() == 0) {
                employeeService.createEmployee(employee);
            } else {
                employeeService.updateEmployee(employee.getId(), employee);
            }
            return "redirect:/employee";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("employees", employeeService.getAllEmployees());
            if (employee.getId() != null) {
                model.addAttribute("employee", employee);
            }
            return "employee/index";
        }
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        try {
            employeeService.deleteEmployee(id);
            return "redirect:/employee";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "employee/index";
        }
    }

    /*
     * Mapeo para las apis
     */
    @GetMapping("/api/employees")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeByIdOrThrow(id));
    }

    @PostMapping("/api/employees")
    @ResponseBody
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {

            if (employee.getSalary() < 0) {
                throw new RuntimeException("El salario no puede ser negativo");
            }
            return ResponseEntity.ok(employeeService.createEmployee(employee));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            if (employee.getSalary() < 0) {
                throw new RuntimeException("El salario no puede ser negativo");
            }
            return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteEmployeeApi(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}