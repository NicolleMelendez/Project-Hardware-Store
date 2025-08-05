package com.hardware.hardwareStore.model;
import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    @Column(name = "supplied_product")
    private String suppliedProduct;

    public Supplier() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSuppliedProduct() {
        return suppliedProduct;
    }

    public void setSuppliedProduct(String suppliedProduct) {
        this.suppliedProduct = suppliedProduct;
    }
}
