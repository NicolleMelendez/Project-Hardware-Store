package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.model.SaleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SaleDetailService {

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    public List<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.findAll();
    }

    public SaleDetail getSaleDetailById(Long id) {
        return saleDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("SaleDetail not found"));
    }

    public SaleDetail createSaleDetail(SaleDetail saleDetail) {
        // Add logic to update inventory stock
        return saleDetailRepository.save(saleDetail);
    }

    public void deleteSaleDetail(Long id) {
        saleDetailRepository.deleteById(id);
    }
}
