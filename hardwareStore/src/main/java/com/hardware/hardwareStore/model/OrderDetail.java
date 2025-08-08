package com.hardware.hardwareStore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order_buy", nullable = false)
    private OrderBuy orderBuy;

    @ManyToOne
    @JoinColumn(name = "id_inventory", nullable = false)
    private Inventory inventory;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "price_unit", nullable = false)
    private Integer priceUnit;

    public OrderDetail() {}

    public OrderDetail(OrderBuy orderBuy, Inventory inventory, Integer amount, Integer priceUnit) {
        this.orderBuy = orderBuy;
        this.inventory = inventory;
        this.amount = amount;
        this.priceUnit = priceUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }
}