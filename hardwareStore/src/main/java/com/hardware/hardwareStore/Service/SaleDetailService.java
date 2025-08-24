package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Inventory;
import com.hardware.hardwareStore.model.Sale;
import com.hardware.hardwareStore.model.SaleDetail;
import com.hardware.hardwareStore.Repository.InventoryRepository;
import com.hardware.hardwareStore.Repository.SaleDetailRepository;
import com.hardware.hardwareStore.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SaleDetailService {

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Autowired
    private SaleRepository saleRepository; // Necesario para actualizar la Venta

    @Autowired
    private InventoryRepository inventoryRepository; // Necesario para manejar el stock

    /**
     * Busca todos los detalles asociados a un ID de venta.
     * @param saleId El ID de la venta.
     * @return Una lista de detalles de venta.
     */
    public List<SaleDetail> findBySaleId(Long saleId) {
        return saleDetailRepository.findBySaleId(saleId);
    }

    /**
     * Guarda un nuevo detalle de venta (añade un producto a una venta).
     * Este método contiene la lógica de negocio principal.
     * @param saleDetail El nuevo detalle de venta a guardar.
     * @return El detalle de venta guardado.
     */
    public SaleDetail save(SaleDetail saleDetail) {
        // 1. Validar que la venta y el producto existan en la base de datos
        Sale sale = saleRepository.findById(saleDetail.getSale().getId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + saleDetail.getSale().getId()));

        Inventory inventory = inventoryRepository.findById(saleDetail.getInventory().getId())
                .orElseThrow(() -> new RuntimeException("Producto de inventario no encontrado con ID: " + saleDetail.getInventory().getId()));

        // 2. Validar si hay stock suficiente
        if (inventory.getStock() < saleDetail.getAmount()) {
            throw new RuntimeException("No hay stock suficiente para el producto: " + inventory.getName());
        }

        // 3. Descontar la cantidad vendida del stock del inventario
        inventory.setStock(inventory.getStock() - saleDetail.getAmount());
        inventoryRepository.save(inventory);

        // 4. Guardar el nuevo detalle de la venta
        SaleDetail savedDetail = saleDetailRepository.save(saleDetail);

        // 5. Recalcular el total de la venta principal y guardarla
        recalculateSaleTotal(sale.getId());

        return savedDetail;
    }

    /**
     * Método privado para recalcular el total de una venta basado en sus detalles.
     * @param saleId El ID de la venta a recalcular.
     */
    private void recalculateSaleTotal(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        List<SaleDetail> details = saleDetailRepository.findBySaleId(saleId);

        // Suma el (precio * cantidad) de cada detalle para obtener el nuevo total
        int total = details.stream()
                .mapToInt(detail -> detail.getAmount() * detail.getPriceUnit())
                .sum();

        sale.setTotal(total);
        saleRepository.save(sale);
    }
}