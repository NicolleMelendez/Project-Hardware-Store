package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {

    // Spring Data JPA crea la consulta automáticamente por el nombre del método
    List<SaleDetail> findBySaleId(Long saleId);
}