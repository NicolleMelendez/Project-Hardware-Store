package com.hardware.hardwareStore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_detail")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("orderBuy")
    @JoinColumn(name = "id_order_buy")
    @JsonBackReference
    private OrderBuy orderBuy;

    @ManyToOne
    @MapsId("inventory")
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Integer priceUnit;
}