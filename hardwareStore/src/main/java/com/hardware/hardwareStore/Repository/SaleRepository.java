package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByStatus(String status);

    @Query("SELECT s FROM Sale s WHERE s.client.id = :clientId")
    List<Sale> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT s FROM Sale s WHERE s.employee.id = :employeeId")
    List<Sale> findByEmployeeId(@Param("employeeId") Long employeeId);

    List<Sale> findByDateSaleBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findAllByOrderByDateSaleDesc();

    @Query("SELECT s FROM Sale s WHERE s.client.id = :clientId ORDER BY s.dateSale DESC")
    List<Sale> findByClientIdOrderByDateDesc(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.status = :status")
    Long countByStatus(@Param("status") String status);

    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.status = 'COMPLETADA'")
    Double getTotalCompletedSales();

    @Query("SELECT s FROM Sale s WHERE LOWER(s.client.name) LIKE LOWER(CONCAT('%', :clientName, '%'))")
    List<Sale> findByClientNameContaining(@Param("clientName") String clientName);
}