package com.hardware.hardwareStore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sale_detail")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Integer priceUnit;
}