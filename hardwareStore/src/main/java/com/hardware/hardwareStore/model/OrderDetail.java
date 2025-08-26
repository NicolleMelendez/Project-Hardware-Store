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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // fetch = LAZY es una buena práctica
    // @MapsId("orderBuy") // <-- SE ELIMINA ESTA LÍNEA
    @JoinColumn(name = "id_order_buy", nullable = false) // 'nullable = false' asegura integridad
    @JsonBackReference
    private OrderBuy orderBuy;

    @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("inventory") // <-- SE ELIMINA ESTA LÍNEA
    @JoinColumn(name = "id_inventory", nullable = false)
    private Inventory inventory;

    private Integer amount;

    @Column(name = "price_unit")
    private Integer priceUnit;
}