package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    @Transactional
    public Sale saveSaleWithDetails(Sale sale, List<Long> productIds, List<Integer> quantities, List<Integer> prices) {
        // Validar que todos los arrays tengan el mismo tamaño
        if (productIds.size() != quantities.size() || productIds.size() != prices.size()) {
            throw new RuntimeException("Los datos de productos no son consistentes");
        }

        // Establecer fechas
        if (sale.getCreatedAt() == null) {
            sale.setCreatedAt(LocalDateTime.now());
        }
        sale.setUpdateAt(LocalDateTime.now());

        // Generar número de factura si no existe
        if (sale.getInvoiceNumber() == null || sale.getInvoiceNumber().isEmpty()) {
            sale.setInvoiceNumber(generateInvoiceNumber());
        }

        // Calcular total basado en los productos
        int total = 0;
        List<SaleDetail> details = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer quantity = quantities.get(i);
            Integer price = prices.get(i);

            if (productId != null && quantity != null && price != null && quantity > 0 && price > 0) {
                Inventory product = inventoryService.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

                // Validar stock
                if (product.getStock() < quantity) {
                    throw new RuntimeException("Stock insuficiente para: " + product.getName() +
                            ". Stock disponible: " + product.getStock());
                }

                // Calcular subtotal
                int subtotal = quantity * price;
                total += subtotal;

                // Crear detalle
                SaleDetail detail = new SaleDetail();
                detail.setInventory(product);
                detail.setAmount(quantity);
                detail.setPriceUnit(price);
                details.add(detail);
            }
        }

        if (details.isEmpty()) {
            throw new RuntimeException("Debe agregar al menos un producto a la venta");
        }

        // Establecer total y guardar venta
        sale.setTotal(total);
        Sale savedSale = saleRepository.save(sale);

        // Guardar detalles y actualizar inventario
        for (SaleDetail detail : details) {
            detail.setSale(savedSale);
            saleDetailRepository.save(detail);

            // Actualizar stock si la venta está COMPLETADA
            if ("COMPLETADA".equals(savedSale.getStatus())) {
                Inventory product = detail.getInventory();
                product.setStock(product.getStock() - detail.getAmount());
                inventoryService.save(product);
            }
        }

        return savedSale;
    }

    @Transactional
    public Sale updateSaleStatus(Long id, String newStatus) {
        Sale sale = getSaleById(id);
        String oldStatus = sale.getStatus();
        sale.setStatus(newStatus);
        sale.setUpdateAt(LocalDateTime.now());

        // De PENDIENTE a COMPLETADA -> Disminuir stock
        if (!"COMPLETADA".equals(oldStatus) && "COMPLETADA".equals(newStatus)) {
            updateInventoryForSale(sale, false); // false para disminuir
        }
        // De COMPLETADA a CANCELADA -> Revertir stock (aumentar)
        else if ("COMPLETADA".equals(oldStatus) && "CANCELADA".equals(newStatus)) {
            updateInventoryForSale(sale, true); // true para aumentar/revertir
        }

        return saleRepository.save(sale);
    }

    @Transactional
    public Sale cancelSale(Long id) {
        return updateSaleStatus(id, "CANCELADA");
    }

    private void updateInventoryForSale(Sale sale, boolean increase) {
        List<SaleDetail> details = saleDetailRepository.findBySaleId(sale.getId());
        for (SaleDetail detail : details) {
            Inventory product = detail.getInventory();
            int quantity = detail.getAmount();
            if (increase) {
                product.setStock(product.getStock() + quantity);
            } else {
                if (product.getStock() < quantity) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
                }
                product.setStock(product.getStock() - quantity);
            }
            inventoryService.save(product);
        }
    }

    public List<SaleDetail> getSaleDetails(Long saleId) {
        return saleDetailRepository.findBySaleId(saleId);
    }

    private String generateInvoiceNumber() {
        return "FACT-" + System.currentTimeMillis();
    }
}