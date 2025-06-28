package com.logistics.order.application.port.inbound.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemCommand(
    @NotBlank(message = "Product ID must not be blank") String productId,
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1") Integer quantity
) {}
