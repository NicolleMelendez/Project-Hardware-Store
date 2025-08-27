package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Sale;
import com.hardware.hardwareStore.model.SaleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByDateSaleBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findAllByOrderByDateSaleDesc();

    @Query("SELECT s FROM Sale s WHERE s.client.id = :clientId ORDER BY s.dateSale DESC")
    List<Sale> findByClientIdOrderByDateDesc(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.status = :status")
    Long countByStatus(@Param("status") String status);

    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.status = 'COMPLETADA'")
    Integer getTotalCompletedSales();


    @Query("SELECT s FROM Sale s WHERE LOWER(s.client.name) LIKE LOWER(CONCAT('%', :clientName, '%'))")
    List<Sale> findByClientNameContaining(@Param("clientName") String clientName);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s WHERE s.status = :status AND s.dateSale >= :startDate AND s.dateSale < :endDate")
    Integer getTotalSalesBetweenDates(@Param("status") SaleStatus status, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.client LEFT JOIN FETCH s.employee ORDER BY s.id DESC")
    List<Sale> findAllWithDetails();

    @Query("SELECT new map(c.name as clientName, SUM(s.total) as total) FROM Sale s JOIN s.client c WHERE s.status = :status GROUP BY c.name ORDER BY total DESC")
    List<Map<String, Object>> findTopCustomers(@Param("status") SaleStatus status, Pageable pageable);

    @Query("SELECT new map(c.name as clientName, SUM(s.total) as total) FROM Sale s JOIN s.client c WHERE s.status = :status AND s.dateSale >= :startDate AND s.dateSale < :endDate GROUP BY c.name ORDER BY total DESC")
    List<Map<String, Object>> findTopCustomersByDateRange(@Param("status") SaleStatus status, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

}