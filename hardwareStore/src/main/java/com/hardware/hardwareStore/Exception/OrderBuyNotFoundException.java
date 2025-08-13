package com.hardware.hardwareStore.Exception;

public class OrderBuyNotFoundException extends RuntimeException {
    public OrderBuyNotFoundException(String message) {
        super(message);
    }
}