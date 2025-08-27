package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import java.util.Map;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>{
    List<SaleDetail> findBySaleId(Long saleId);

    @Query("SELECT new map(i.name as name, SUM(sd.amount) as quantity) FROM SaleDetail sd JOIN sd.inventory i GROUP BY i.name ORDER BY SUM(sd.amount) DESC")
    List<Map<String, Object>> findTopSoldProducts(Pageable pageable);
}
