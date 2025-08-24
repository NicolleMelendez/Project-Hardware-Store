package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.OrderDetailService;
import com.hardware.hardwareStore.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/api/order/{orderId}")
    @ResponseBody
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }
}
