package com.hardware.hardwareStore.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailId implements Serializable {

    private Long orderBuy;  // Coincide con el campo 'orderBuy' en OrderDetail
    private Long inventory; // Coincide con el campo 'inventory' en OrderDetail

    public OrderDetailId() {}

    public OrderDetailId(Long orderBuyId, Long inventoryId) {
        this.orderBuy = orderBuyId;
        this.inventory = inventoryId;
    }

    // Getters, Setters, hashCode y equals
    public Long getOrderBuy() { return orderBuy; }
    public void setOrderBuy(Long orderBuy) { this.orderBuy = orderBuy; }
    public Long getInventory() { return inventory; }
    public void setInventory(Long inventory) { this.inventory = inventory; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(orderBuy, that.orderBuy) && Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBuy, inventory);
    }
}