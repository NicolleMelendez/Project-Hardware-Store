package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SaleDetailService;
import com.hardware.hardwareStore.Service.SaleService;
import com.hardware.hardwareStore.model.SaleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sale-detail")
public class SaleDetailController {

    @Autowired
    private SaleDetailService saleDetailService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String saleDetailPage(Model model) {
        model.addAttribute("details", saleDetailService.getAllSaleDetails());
        model.addAttribute("sales", saleService.getAllSale());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "saledetail/index";
    }

    @PostMapping("/save")
    public String saveSaleDetail(@ModelAttribute SaleDetail detail) {
        saleDetailService.createSaleDetail(detail);
        return "redirect:/sale-detail";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSaleDetail(@PathVariable Long id) {
        saleDetailService.deleteSaleDetail(id);
        return "redirect:/sale-detail";
    }
}
