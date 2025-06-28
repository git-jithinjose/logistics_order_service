package com.logistics.order.domain.model;


public class OrderNumber {
    private final String value;

    public OrderNumber(String value) {
        this.value = value;
    }


    public static OrderNumber of(long sequence) {
        String formatted = String.format("ORD%06d", sequence);
        return new OrderNumber(formatted);
    }

    public String getValue() {
        return value;
    }
}

