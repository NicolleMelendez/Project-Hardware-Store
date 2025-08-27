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
    public OrderBuy saveOrderBuyWithDetails(OrderBuy order, List<Long> productIds, List<Integer> quantities, List<Integer> prices) {
        int total = 0;
        for (int i = 0; i < prices.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }
        order.setTotal(total);
        order.setStatus("PENDIENTE"); // Estado inicial

        // Guarda la orden para obtener un ID
        OrderBuy savedOrder = orderBuyRepository.save(order);

        // Crea y guarda los detalles
        List<OrderDetail> details = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            OrderDetail detail = new OrderDetail();
            Inventory inventory = inventoryService.findById(productIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detail.setInventory(inventory);
            detail.setAmount(quantities.get(i));
            detail.setPriceUnit(prices.get(i));
            detail.setOrderBuy(savedOrder); // Asocia el detalle con la orden guardada
            details.add(detail);
        }
        orderDetailRepository.saveAll(details);
        savedOrder.setOrderDetails(details);

        // Si el estado es "COMPLETADA" al crear, actualiza el stock
        if ("COMPLETADA".equals(order.getStatus())) {
            updateInventoryForOrder(savedOrder, true);
        }

        return savedOrder;
    }

    @Transactional
    public OrderBuy updateStatus(Long id, String newStatus) {
        OrderBuy order = getOrderById(id);
        String oldStatus = order.getStatus();

        if (oldStatus.equals(newStatus)) {
            return order;
        }

        order.setStatus(newStatus);

        // De PENDIENTE a COMPLETADA -> Aumentar stock
        if (!"COMPLETADA".equals(oldStatus) && "COMPLETADA".equals(newStatus)) {
            updateInventoryForOrder(order, true);
        }
        // De COMPLETADA a CANCELADO -> Revertir stock (disminuir)
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