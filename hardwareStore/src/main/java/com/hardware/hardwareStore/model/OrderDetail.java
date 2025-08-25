package com.hardware.hardwareStore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("orderBuy")
    @JoinColumn(name = "id_order_buy")
    private OrderBuy orderBuy;

    @ManyToOne
    @MapsId("inventory")
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Integer priceUnit;

    // Getters y Setters
    public OrderDetailId getId() { return id; }
    public void setId(OrderDetailId id) { this.id = id; }
    public OrderBuy getOrderBuy() { return orderBuy; }
    public void setOrderBuy(OrderBuy orderBuy) { this.orderBuy = orderBuy; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public Integer getPriceUnit() { return priceUnit; }
    public void setPriceUnit(Integer priceUnit) { this.priceUnit = priceUnit; }
}