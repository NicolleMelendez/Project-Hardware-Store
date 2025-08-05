package com.hardware.hardwareStore.model;
import jakarta.persistence.*;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne
    @JoinColumn(name = "id_sale")
    private  Sale sale;

    @ManyToOne
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Float priceUnit;

    public SaleDetail() {}

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
