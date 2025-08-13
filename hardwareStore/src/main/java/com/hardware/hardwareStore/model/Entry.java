package com.hardware.hardwareStore.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "price_buy", nullable = false)
    private Double priceBuy;

    @Column(name = "date_entry", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntry = new Date();
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Entry() {}

    public Entry(Inventory inventory, Supplier supplier, Integer amount,
                 Double priceBuy, Employee employee) {
        this.inventory = inventory;
        this.supplier = supplier;
        this.amount = amount;
        this.priceBuy = priceBuy;
        this.employee = employee;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(Double priceBuy) {
        this.priceBuy = priceBuy;
    }

    public Date getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(Date dateEntry) {
        this.dateEntry = dateEntry;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}