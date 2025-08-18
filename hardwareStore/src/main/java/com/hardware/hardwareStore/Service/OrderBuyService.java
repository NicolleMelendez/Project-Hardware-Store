package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Exception.OrderBuyNotFoundException;
import com.hardware.hardwareStore.model.OrderBuy;
import com.hardware.hardwareStore.Repository.OrderBuyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderBuyService {

    private final OrderBuyRepository orderBuyRepository;

    public OrderBuyService(OrderBuyRepository orderBuyRepository) {
        this.orderBuyRepository = orderBuyRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderBuy> getAllOrders() {
        return orderBuyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderBuy getOrderById(Long id) {
        return orderBuyRepository.findById(id)
                .orElseThrow(() -> new OrderBuyNotFoundException("No se encontro la órden: " + id));
    }

    @Transactional
    public OrderBuy createOrder(OrderBuy orderBuy) {
        return orderBuyRepository.save(orderBuy);
    }

    @Transactional
    public OrderBuy updateOrder(Long id, OrderBuy orderDetails) {
        OrderBuy order = getOrderById(id);

        // Actualizae los campos
        order.setDateOrder(orderDetails.getDateOrder());
        order.setTotal(orderDetails.getTotal());


        return orderBuyRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderBuyRepository.existsById(id)) {
            throw new OrderBuyNotFoundException("No se encontro la órden: " + id);
        }
        orderBuyRepository.deleteById(id);
    }
}