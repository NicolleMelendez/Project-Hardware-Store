package com.hardware.hardwareStore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sale", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "id_inventory", nullable = false)
    private Inventory inventory;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "price_unit", nullable = false)
    private Float priceUnit;

    public SaleDetail() {}

    public SaleDetail(Sale sale, Inventory inventory, Integer amount, Float priceUnit) {
        this.sale = sale;
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
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
