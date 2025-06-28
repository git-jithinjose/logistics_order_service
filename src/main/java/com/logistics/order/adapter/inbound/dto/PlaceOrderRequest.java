package com.logistics.order.adapter.inbound.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlaceOrderRequest(

		@NotNull(message = "Customer ID is required") Long customerId,

		@NotNull(message = "Items list must not be null") @Size(min = 1,
		message = "Number of items in an order must be at least 1")@Valid List<OrderItemRequest> items,
		@NotBlank(message = "Customer ID is mandatory") String shippingAddressId) {
}
