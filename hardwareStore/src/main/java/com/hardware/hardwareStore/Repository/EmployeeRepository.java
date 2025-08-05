package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
