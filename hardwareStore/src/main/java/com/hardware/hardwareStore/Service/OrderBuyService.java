package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Exception.OrderBuyNotFoundException;
import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.OrderBuyRepository;
import com.hardware.hardwareStore.Repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderBuyService {

    private final OrderBuyRepository orderBuyRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final InventoryService inventoryService;

    // ... (los otros métodos como getAllOrders, getOrderById, etc., quedan igual) ...
    @Transactional(readOnly = true)
    public List<OrderBuy> getAllOrders() {
        return orderBuyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderBuy getOrderById(Long id) {
        return orderBuyRepository.findById(id)
                .orElseThrow(() -> new OrderBuyNotFoundException("No se encontro la órden: " + id));
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderBuyId(orderId);
    }

    @Transactional
    public OrderBuy saveOrderBuyWithDetails(OrderBuy orderBuy, List<Long> productIds, List<Integer> quantities, List<Integer> prices) {
        if (productIds == null || quantities == null || prices == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto al pedido.");
        }
        if (productIds.size() != quantities.size() || productIds.size() != prices.size()) {
            throw new IllegalArgumentException("Los datos de los productos no son consistentes.");
        }

        // Primero guardamos la orden para que obtenga un ID
        // Esto es importante para establecer la relación bidireccional
        orderBuy.setOrderDetails(new ArrayList<>()); // Inicializa la lista vacía por ahora
        OrderBuy savedOrder = orderBuyRepository.save(orderBuy);

        int total = 0;
        List<OrderDetail> details = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            int quantity = quantities.get(i);
            int price = prices.get(i);

            total += quantity * price;

            Inventory product = inventoryService.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

            // --- SECCIÓN CORREGIDA ---
            // 1. Crea una nueva instancia de OrderDetail
            OrderDetail detail = new OrderDetail();

            // 2. Establece las relaciones y los datos
            detail.setOrderBuy(savedOrder); // Asigna la orden ya guardada
            detail.setInventory(product);
            detail.setAmount(quantity);
            detail.setPriceUnit(price);

            // 3. Agrega el detalle a la lista
            details.add(detail);
        }

        savedOrder.setTotal(total);
        savedOrder.setOrderDetails(details);

        // Si el estado es "COMPLETADA", se actualiza el stock.
        if ("COMPLETADA".equals(savedOrder.getStatus())) {
            updateInventoryForOrder(savedOrder, true);
        }

        // No es necesario volver a guardar, la transacción se encargará al finalizar.
        return savedOrder;
    }

    // ... (el resto de tus métodos: updateStatus, cancelOrder, updateInventoryForOrder) ...
    @Transactional
    public OrderBuy updateStatus(Long id, String newStatus) {
        OrderBuy order = getOrderById(id);
        String oldStatus = order.getStatus();

        // Si no hay cambio de estado, no hacer nada.
        if (oldStatus.equals(newStatus)) {
            return order;
        }

        order.setStatus(newStatus);

        // De "PENDIENTE" a "COMPLETADA" -> Aumentar stock
        if (!"COMPLETADA".equals(oldStatus) && "COMPLETADA".equals(newStatus)) {
            updateInventoryForOrder(order, true);
        }
        // De "COMPLETADA" a "CANCELADO" -> Revertir stock (disminuir)
        else if ("COMPLETADA".equals(oldStatus) && "CANCELADO".equals(newStatus)) {
            updateInventoryForOrder(order, false);
        }

        return orderBuyRepository.save(order);
    }

    @Transactional
    public OrderBuy cancelOrder(Long id) {
        return updateStatus(id, "CANCELADO");
    }

    private void updateInventoryForOrder(OrderBuy order, boolean increase) {
        List<OrderDetail> details = getOrderDetails(order.getId());
        for (OrderDetail detail : details) {
            Inventory product = detail.getInventory();
            int quantity = detail.getAmount();
            if (increase) {
                product.setStock(product.getStock() + quantity);
            } else {
                product.setStock(product.getStock() - quantity);
            }
            inventoryService.save(product);
        }
    }
}