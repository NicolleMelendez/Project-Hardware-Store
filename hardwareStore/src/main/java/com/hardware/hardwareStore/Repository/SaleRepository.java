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
    List<Sale> findByDateSaleBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findAllByOrderByDateSaleDesc();

    @Query("SELECT s FROM Sale s WHERE s.client.id = :clientId ORDER BY s.dateSale DESC")
    List<Sale> findByClientIdOrderByDateDesc(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.status = :status")
    Long countByStatus(@Param("status") String status);

    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.status = 'COMPLETADA'")
    Integer getTotalCompletedSales();

    // CORREGIDO: Usar 'name' en lugar de 'firstName' y 'lastName' para Client
    @Query("SELECT s FROM Sale s WHERE LOWER(s.client.name) LIKE LOWER(CONCAT('%', :clientName, '%'))")
    List<Sale> findByClientNameContaining(@Param("clientName") String clientName);

    // TEMPORAL: Comentado hasta verificar modelo Employee
    // @Query("SELECT new map(e.name as employeeName, sum(s.total) as total) FROM Sale s JOIN s.employee e GROUP BY e.name ORDER BY total DESC")
    // List<java.util.Map<String, Object>> findTop5Employees();

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s WHERE s.status = 'COMPLETADA' AND s.dateSale >= :startDate AND s.dateSale < :endDate")
    Integer getTotalSalesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // CORREGIDO: Usar 'name' en lugar de 'firstName' y 'lastName' para Client
    //@Query("SELECT new map(c.name as clientName, sum(s.total) as total) FROM Sale s JOIN s.client c GROUP BY c.name ORDER BY total DESC")
    //List<java.util.Map<String, Object>> findTop5Customers();

    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.client LEFT JOIN FETCH s.employee ORDER BY s.id DESC")
    List<Sale> findAllWithDetails();
}