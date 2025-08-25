package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Autowired
    private SaleRepository saleRepository;



    public List<Map<String, Object>> getTop5Customers() {
        return saleRepository.findTop5Customers();
    }

    public List<Map<String, Object>> getTop5Employees() {
        return saleRepository.findTop5Employees();
    }
}
