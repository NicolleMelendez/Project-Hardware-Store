package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    // Consulta derivada del nombre del método
    List<Employee> findByPosition(String position);

    // Consulta con anotación @Query
    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<Employee> findWithSalaryGreaterThan(@Param("minSalary") Integer minSalary);
}
