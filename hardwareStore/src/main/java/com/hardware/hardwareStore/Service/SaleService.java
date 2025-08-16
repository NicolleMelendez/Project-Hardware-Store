package com.hardware.hardwareStore.Service;

<<<<<<< HEAD

import com.hardware.hardwareStore.model.Sale;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
=======
import com.hardware.hardwareStore.Repository.SaleRepository;
import com.hardware.hardwareStore.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
>>>>>>> origin/nicolle
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

<<<<<<< HEAD
    public List<Sale> findAll() {
        return saleRepository.findAllByOrderByDateSaleDesc();
    }

    public Sale findById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.orElse(null);
    }

    public Sale save(Sale sale) {
        validateSale(sale);
        return saleRepository.save(sale);
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
=======
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public Sale createSale(Sale sale) {
        // Here you can add logic to update inventory, etc.
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
>>>>>>> origin/nicolle
