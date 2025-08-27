package com.hardware.hardwareStore.model;

public enum OrderStatus {
    PENDIENTE("Pendiente"),
    PROCESADO("Procesado"),
    ENVIADO("Enviado"),
    CANCELADO("Cancelado");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}