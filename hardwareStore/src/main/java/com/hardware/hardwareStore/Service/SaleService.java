package com.hardware.hardwareStore.Service;


import com.hardware.hardwareStore.Exception.OrderBuyNotFoundException;
import com.hardware.hardwareStore.Exception.SaleNotFoundException;
import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.model.OrderBuy;
import com.hardware.hardwareStore.model.Sale;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> findAll() {

        return saleRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("No se encontro la venta: " + id));
    }


    public Sale findById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.orElse(null);
    }


    public Sale save(Sale sale) {
        validateSale(sale);
        return saleRepository.save(sale);
    }

    @Transactional
    public void delete(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException("No se encontro la venta: " + id);
        }
        saleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }

    public List<Sale> findByStatus(String status) {
        return saleRepository.findByStatus(status);
    }



    public List<Sale> findByClientId(Long clientId) {
        return saleRepository.findByClientId(clientId);
    }

    public List<Sale> findByEmployeeId(Long employeeId) {
        return saleRepository.findByEmployeeId(employeeId);
    }

    private void validateSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("La venta no puede ser null");
        }

        if (sale.getClient() == null || sale.getClient().getId() == null) {
            throw new IllegalArgumentException("El cliente es requerido");
        }

        if (sale.getEmployee() == null || sale.getEmployee().getId() == null) {
            throw new IllegalArgumentException("El empleado es requerido");
        }

        if (sale.getDateSale() == null) {
            throw new IllegalArgumentException("La fecha de venta es requerida");
        }

        if (sale.getTotal() == null || sale.getTotal() < 0) {
            throw new IllegalArgumentException("El total debe ser mayor o igual a 0");
        }

        if (sale.getStatus() == null || sale.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado es requerido");
        }

        String[] validStatuses = {"COMPLETADA", "PENDIENTE", "CANCELADA"};
        boolean validStatus = false;
        for (String status : validStatuses) {
            if (status.equals(sale.getStatus())) {
                validStatus = true;
                break;
            }
        }
        if (!validStatus) {
            throw new IllegalArgumentException("Estado no válido. Debe ser: COMPLETADA, PENDIENTE o CANCELADA");
        }
    }
}





