package com.logistics.order.domain.model;

public final class ProductId {
    private final String value;

    public ProductId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ProductId cannot be null or empty");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
