package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final InventoryService inventoryService;

    public SaleService(SaleRepository saleRepository,
                       SaleDetailRepository saleDetailRepository,
                       InventoryService inventoryService) {
        this.saleRepository = saleRepository;
        this.saleDetailRepository = saleDetailRepository;
        this.inventoryService = inventoryService;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAllWithDetails();
    }

    @Transactional(readOnly = true)
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    @Transactional
    public Sale saveSaleWithDetails(Sale sale, List<Long> productIds, List<Integer> quantities, List<Integer> prices) {
        // --- 1. Asignar valores por defecto a la Venta ---
        sale.setDateSale(LocalDate.now());
        sale.setUpdateAt(LocalDateTime.now());
        if (sale.getCreatedAt() == null) {
            sale.setCreatedAt(LocalDateTime.now());
        }
        if (sale.getInvoiceNumber() == null || sale.getInvoiceNumber().isEmpty()) {
            sale.setInvoiceNumber(generateInvoiceNumber());
        }

        // --- 2. Procesar y vincular los detalles (USANDO INTEGER) ---
        sale.getSaleDetails().clear();
        int total = 0; // El total se declara como int

        for (int i = 0; i < productIds.size(); i++) {
            Inventory product = inventoryService.findById(productIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Integer quantity = quantities.get(i);
            Integer price = prices.get(i);

            if (product.getStock() < quantity) {
                throw new RuntimeException("Stock insuficiente para: " + product.getName());
            }

            total += quantity * price; // La suma es entre enteros

            SaleDetail detail = new SaleDetail();
            detail.setInventory(product);
            detail.setAmount(quantity);
            detail.setPriceUnit(price); // El precio se asigna como Integer
            detail.setSale(sale);
            sale.getSaleDetails().add(detail);
        }

        if (sale.getSaleDetails().isEmpty()) {
            throw new RuntimeException("Debe agregar al menos un producto a la venta");
        }

        sale.setTotal(total); // El total se asigna como Integer

        // --- 3. Guardar todo en una sola operación ---
        Sale savedSale = saleRepository.save(sale);

        // 4. Descontar stock si el estado inicial es COMPLETADA
        if (savedSale.getStatus() == SaleStatus.COMPLETADA) {
            updateInventoryForSale(savedSale, false);
        }

        return savedSale;
    }

    @Transactional
    public Sale updateSaleStatus(Long id, SaleStatus newStatus) {
        Sale sale = getSaleById(id);
        SaleStatus oldStatus = sale.getStatus();
        if (oldStatus == newStatus) return sale;

        sale.setStatus(newStatus);
        sale.setUpdateAt(LocalDateTime.now());

        if (oldStatus != SaleStatus.COMPLETADA && newStatus == SaleStatus.COMPLETADA) {
            updateInventoryForSale(sale, false);
        } else if (oldStatus == SaleStatus.COMPLETADA && newStatus != SaleStatus.COMPLETADA) {
            updateInventoryForSale(sale, true);
        }
        return saleRepository.save(sale);
    }

    @Transactional
    public Sale cancelSale(Long id) {
        return updateSaleStatus(id, SaleStatus.CANCELADA);
    }

    private void updateInventoryForSale(Sale sale, boolean revert) {
        List<SaleDetail> details = saleDetailRepository.findBySaleId(sale.getId());
        for (SaleDetail detail : details) {
            Inventory product = detail.getInventory();
            if (revert) {
                product.setStock(product.getStock() + detail.getAmount());
            } else {
                if (product.getStock() < detail.getAmount()) {
                    throw new RuntimeException("Stock insuficiente para: " + product.getName());
                }
                product.setStock(product.getStock() - detail.getAmount());
            }
            inventoryService.save(product);
        }
    }

    @Transactional(readOnly = true)
    public List<SaleDetail> getSaleDetails(Long saleId) {
        return saleDetailRepository.findBySaleId(saleId);
    }

    private String generateInvoiceNumber() {
        return "FACT-" + System.currentTimeMillis();
    }
}