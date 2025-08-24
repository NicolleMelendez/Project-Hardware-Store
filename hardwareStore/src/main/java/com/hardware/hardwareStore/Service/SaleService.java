package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.Exception.SaleNotFoundException;
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

    // Se mantiene igual para crear nuevas ventas
    public Sale save(Sale sale) {
        if (sale.getId() == null) {
            sale.setStatus("PENDIENTE");
        }

        validateNewSale(sale);
        return saleRepository.save(sale);
    }

    // ¡NUEVO MÉTODO! Específico para actualizar solo el estado.
    public Sale updateStatus(Long saleId, String status) {
        if (status == null || status.trim().isEmpty() || !List.of("COMPLETADA", "PENDIENTE", "CANCELADA").contains(status)) {
            throw new IllegalArgumentException("Estado no válido.");
        }

        Sale sale = findById(saleId); // Reutilizamos el método para encontrar la venta
        sale.setStatus(status);
        return saleRepository.save(sale); // Guardamos la venta con el estado actualizado
    }

    @Transactional(readOnly = true)
    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("No se encontró la venta con ID: " + id));
    }

    private void validateNewSale(Sale sale) {
        if (sale.getClient() == null || sale.getClient().getId() == null) throw new IllegalArgumentException("El cliente es requerido.");
        if (sale.getEmployee() == null || sale.getEmployee().getId() == null) throw new IllegalArgumentException("El empleado es requerido.");
        if (sale.getDateSale() == null) throw new IllegalArgumentException("La fecha de venta es requerida.");
    }
}