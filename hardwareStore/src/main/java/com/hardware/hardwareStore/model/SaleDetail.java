package com.hardware.hardwareStore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @EmbeddedId
    private SaleDetailId id;

    @ManyToOne
    @MapsId("sale") // Conecta el campo 'sale' de SaleDetailId a esta relación
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @ManyToOne
    @MapsId("inventory") // Conecta el campo 'inventory' de SaleDetailId a esta relación
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Integer priceUnit;

    // Getters y Setters
    public SaleDetailId getId() { return id; }
    public void setId(SaleDetailId id) { this.id = id; }
    public Sale getSale() { return sale; }
    public void setSale(Sale sale) { this.sale = sale; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public Integer getPriceUnit() { return priceUnit; }
    public void setPriceUnit(Integer priceUnit) { this.priceUnit = priceUnit; }
}