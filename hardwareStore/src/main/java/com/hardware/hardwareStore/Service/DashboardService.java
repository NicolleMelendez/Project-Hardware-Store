package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.InventoryRepository;
import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.Repository.SaleRepository;
import com.hardware.hardwareStore.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // --- Reporte de Productos ---
    public List<Map<String, Object>> getTop5SoldProducts() {
        return saleDetailRepository.findTop5SoldProducts();
    }

    public List<Inventory> getLowStockProducts() {
        return inventoryRepository.findLowStockProducts();
    }

    // --- Reporte de Clientes y Empleados ---
    public List<Map<String, Object>> getTop5Customers() {
        return saleRepository.findTop5Customers();
    }

    public List<Map<String, Object>> getTop5Employees() {
        return saleRepository.findTop5Employees();
    }

    // --- Reportes de Ventas ---
    public Integer getDailySalesTotal() {
        LocalDate today = LocalDate.now();
        return saleRepository.getTotalSalesBetweenDates(today, today.plusDays(1));
    }

    public Integer getWeeklySalesTotal() {
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusWeeks(1);
        return saleRepository.getTotalSalesBetweenDates(startOfWeek, endOfWeek);
    }

    public Integer getMonthlySalesTotal() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1);
        return saleRepository.getTotalSalesBetweenDates(startOfMonth, endOfMonth);
    }
}
}

