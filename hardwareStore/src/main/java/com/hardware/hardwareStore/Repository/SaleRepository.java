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
    List<Sale> findAllByOrderByDateSaleDesc();

    @Query("SELECT s FROM Sale s WHERE s.status = :status ORDER BY s.dateSale DESC")
    List<Sale> findByStatus(@Param("status") String status);

    @Query("SELECT s FROM Sale s WHERE s.client.id = :clientId ORDER BY s.dateSale DESC")
    List<Sale> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT s FROM Sale s WHERE s.employee.id = :employeeId ORDER BY s.dateSale DESC")
    List<Sale> findByEmployeeId(@Param("employeeId") Long employeeId);
}