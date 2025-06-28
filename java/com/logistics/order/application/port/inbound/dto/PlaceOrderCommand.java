package com.logistics.order.application.port.inbound.dto;



import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PlaceOrderCommand(
    @NotEmpty(message ="OrderItems must be at least 1")
    List<OrderItemCommand> items,
    @NotBlank(message = "Customer ID must not be blank") Long customerId,
    @NotBlank(message = "Shipping Address ID must not be blank") String shippingAddressId
) { }

