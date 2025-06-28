package com.logistics.order.domain.model;

import java.util.Objects;

public final class OrderItem {
    private final ProductId productId;
    private final int quantity;

    public OrderItem(ProductId productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.productId = Objects.requireNonNull(productId, "ProductId is required");
        this.quantity = quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
