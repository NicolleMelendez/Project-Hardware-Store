package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Repository.OrderDetailRepository;
import com.hardware.hardwareStore.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderBuyId(orderId);
    }
}
