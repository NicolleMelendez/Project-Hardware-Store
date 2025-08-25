package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Sale;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    /**
     * Devuelve una lista de todas las ventas.
     * Este es el método que tu controlador está buscando.
     */
    @Transactional(readOnly = true)
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    /**
     * Guarda una nueva venta o actualiza una existente.
     */
    public Sale save(Sale sale) {
        if (sale.getId() == null) {
            // Para una venta nueva, solo asignamos el estado.
            // El total y los demás datos vienen del formulario.
            sale.setStatus("PENDIENTE");
        }
        validateSale(sale);
        return saleRepository.save(sale);
    }

    /**
     * Actualiza únicamente el estado de una venta existente.
     */
    public Sale updateStatus(Long saleId, String status) {
        if (status == null || status.trim().isEmpty() || !List.of("COMPLETADA", "PENDIENTE", "CANCELADA").contains(status)) {
            throw new IllegalArgumentException("Estado no válido.");
        }
        Sale sale = findById(saleId);
        sale.setStatus(status);
        return saleRepository.save(sale);
    }

    /**
     * Busca una venta por su ID.
     */
    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la venta con ID: " + id));
    }

    /**
     * Valida los datos principales de una venta.
     */
    private void validateSale(Sale sale) {
        if (sale.getClient() == null || sale.getEmployee() == null || sale.getDateSale() == null) {
            throw new IllegalArgumentException("Cliente, empleado y fecha son requeridos.");
        }
        if (sale.getTotal() == null || sale.getTotal() < 0) {
            throw new IllegalArgumentException("El total es requerido y no puede ser negativo.");
        }
    }


}