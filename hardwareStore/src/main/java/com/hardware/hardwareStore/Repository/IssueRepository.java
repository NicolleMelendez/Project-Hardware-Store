package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>{
    @Query("SELECT i FROM Issue i WHERE i.inventory.id = :inventoryId")
    List<Issue> findByInventoryId(@Param("inventoryId") Long inventoryId);

    @Query("SELECT i FROM Issue i WHERE i.employee.id = :employeeId")
    List<Issue> findByEmployeeId(@Param("employeeId") Long employeeId);

    List<Issue> findByDateIssueBetween(Date startDate, Date endDate);

    List<Issue> findByReasonContainingIgnoreCase(String reason);
}
