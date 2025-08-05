package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long>{
}
