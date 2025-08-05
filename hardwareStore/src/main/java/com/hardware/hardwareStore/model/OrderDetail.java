package com.hardware.hardwareStore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne
    @JoinColumn(name = "id_order_buy")
    private OrderBuy orderBuy;

    @ManyToOne
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Float priceUnit;

    public OrderDetail() {}

    public OrderBuy getOrderBuy() {
        return orderBuy;
    }

    public void setOrderBuy(OrderBuy orderBuy) {
        this.orderBuy = orderBuy;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Float priceUnit) {
        this.priceUnit = priceUnit;
    }
}
