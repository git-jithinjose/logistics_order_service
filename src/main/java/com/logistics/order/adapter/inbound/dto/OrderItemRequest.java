package com.logistics.order.adapter.inbound.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record OrderItemRequest(
	    @NotBlank(message = "Product ID must not be blank") String productId,
	    @NotNull(message = "Quantity is required")
	    Integer quantity
	) {}
