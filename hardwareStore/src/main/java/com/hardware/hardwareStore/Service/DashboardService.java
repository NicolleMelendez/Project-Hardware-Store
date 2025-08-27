package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.Repository.SaleRepository;
import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.model.SaleStatus;
import org.springframework.data.domain.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DashboardService {

    private final SaleDetailRepository saleDetailRepository;
    private final SaleRepository saleRepository;
    private final InventoryService inventoryService;

    // AÑADE estos métodos si no los tienes:
    public List<Map<String, Object>> getTop5SoldProducts() {
        return saleDetailRepository.findTopSoldProducts(PageRequest.of(0, 5));
    }

    public List<Inventory> getLowStockProducts() {
        return inventoryService.getLowStockItems();
    }

    public List<Map<String, Object>> getTop5Customers() {
        return saleRepository.findTopCustomers(SaleStatus.COMPLETADA, PageRequest.of(0, 5));
    }

    public Integer getDailySalesTotal() {
        LocalDate today = LocalDate.now();
        return saleRepository.getTotalSalesBetweenDates(SaleStatus.COMPLETADA, today, today.plusDays(1));
    }

    public Integer getWeeklySalesTotal() {
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusWeeks(1);
        return saleRepository.getTotalSalesBetweenDates(SaleStatus.COMPLETADA, startOfWeek, endOfWeek);
    }

    public Integer getMonthlySalesTotal() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1);
        return saleRepository.getTotalSalesBetweenDates(SaleStatus.COMPLETADA, startOfMonth, endOfMonth);
    }
}