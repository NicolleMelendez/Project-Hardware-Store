package com.hardware.hardwareStore.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SaleDetailId implements Serializable {

    private Long sale;      // Coincide con el campo 'sale' en SaleDetail
    private Long inventory; // Coincide con el campo 'inventory' en SaleDetail

    // Constructor vacío requerido por JPA
    public SaleDetailId() {}

    public SaleDetailId(Long saleId, Long inventoryId) {
        this.sale = saleId;
        this.inventory = inventoryId;
    }

    // Getters, Setters, y los OBLIGATORIOS hashCode y equals
    public Long getSale() { return sale; }
    public void setSale(Long sale) { this.sale = sale; }
    public Long getInventory() { return inventory; }
    public void setInventory(Long inventory) { this.inventory = inventory; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDetailId that = (SaleDetailId) o;
        return Objects.equals(sale, that.sale) && Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, inventory);
    }
}