package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.SaleDetailService;
import com.hardware.hardwareStore.model.SaleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-details")
public class SaleDetailController {

    @Autowired
    private SaleDetailService saleDetailService;

    // API para obtener los detalles de una venta específica
    // JS llamará a esta URL: GET /api/sale-details/sale/{id}
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleDetail>> getDetailsBySaleId(@PathVariable Long saleId) {
        List<SaleDetail> details = saleDetailService.findBySaleId(saleId);
        return ResponseEntity.ok(details);
    }

    // API para guardar un nuevo detalle de venta (añadir un producto)
    // JS llamará a esta URL: POST /api/sale-details
    @PostMapping
    public ResponseEntity<?> saveSaleDetail(@RequestBody SaleDetail saleDetail) {
        try {
            SaleDetail savedDetail = saleDetailService.save(saleDetail);
            return ResponseEntity.ok(savedDetail);
        } catch (RuntimeException e) {
            // Devuelve el mensaje de error (ej. "No hay stock suficiente") al frontend
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}