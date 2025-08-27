package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.*;
import com.hardware.hardwareStore.Repository.OrderDetailRepository;
import com.hardware.hardwareStore.Repository.OrderBuyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderBuyService {
    private final OrderBuyRepository orderBuyRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final InventoryService inventoryService;

    public OrderBuyService(OrderBuyRepository orderBuyRepository,
                           OrderDetailRepository orderDetailRepository,
                           InventoryService inventoryService) {
        this.orderBuyRepository = orderBuyRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.inventoryService = inventoryService;
    }

    public List<OrderBuy> getAllOrders() {
        return orderBuyRepository.findAllWithDetails();
    }

    @Transactional(readOnly = true)
    public OrderBuy getOrderById(Long id) {
        return orderBuyRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada con ID: " + id));
    }

    @Transactional
    public OrderBuy saveOrderWithDetails(OrderBuy order, List<Long> productIds, List<Integer> quantities, List<Integer> prices) {
        order.setDateOrder(LocalDate.now());
        order.setUpdateAt(LocalDateTime.now());
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now());
        }

        order.getOrderDetails().clear();
        int total = 0;

        for (int i = 0; i < productIds.size(); i++) {
            Inventory product = inventoryService.findById(productIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Integer quantity = quantities.get(i);
            Integer price = prices.get(i);
            total += quantity * price;

            OrderDetail detail = new OrderDetail();
            detail.setInventory(product);
            detail.setAmount(quantity);
            detail.setPriceUnit(price);
            detail.setOrderBuy(order);
            order.getOrderDetails().add(detail);
        }

        order.setTotal(total);
        return orderBuyRepository.save(order);
    }

    @Transactional
    public OrderBuy updateOrderStatus(Long id, OrderStatus newStatus) {
        OrderBuy order = getOrderById(id);
        OrderStatus oldStatus = order.getStatus();
        if (oldStatus == newStatus) return order;

        order.setStatus(newStatus);
        order.setUpdateAt(LocalDateTime.now());

        // De PENDIENTE a PROCESADO -> Aumentar stock
        if (oldStatus == OrderStatus.PENDIENTE && newStatus == OrderStatus.PROCESADO) {
            updateInventoryForOrder(order, true); // true = Aumentar
        }
        // De PROCESADO a cualquier otro estado (PENDIENTE o CANCELADO) -> Revertir (disminuir) stock
        else if (oldStatus == OrderStatus.PROCESADO && (newStatus == OrderStatus.PENDIENTE || newStatus == OrderStatus.CANCELADO)) {
            updateInventoryForOrder(order, false); // false = Disminuir
        }
        return orderBuyRepository.save(order);
    }

    private void updateInventoryForOrder(OrderBuy order, boolean increase) {
        List<OrderDetail> details = orderDetailRepository.findByOrderBuyId(order.getId());
        for (OrderDetail detail : details) {
            Inventory product = detail.getInventory();
            if (increase) {
                product.setStock(product.getStock() + detail.getAmount());
            } else {
                if (product.getStock() < detail.getAmount()) {
                    throw new RuntimeException("No se puede revertir el stock, quedaría en negativo para: " + product.getName());
                }
                product.setStock(product.getStock() - detail.getAmount());
            }
            inventoryService.save(product);
        }
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderBuyId(orderId);
    }
}