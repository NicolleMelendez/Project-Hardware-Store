package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>{
    List<SaleDetail> findBySaleId(Long saleId);

    @Query("SELECT sd FROM SaleDetail sd WHERE sd.sale.id = :saleId")
    List<SaleDetail> findDetailsBySaleId(@Param("saleId") Long saleId);
}
