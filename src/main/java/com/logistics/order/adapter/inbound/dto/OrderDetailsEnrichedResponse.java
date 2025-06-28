package com.logistics.order.adapter.inbound.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailsEnrichedResponse(

		Long customerNumber,
		String orderNumber,
	    String status,
		List<OrderItemEnrichedResponse> orderItems,
		String shippingAddress,
		BigDecimal totalOrderValue,
		String currency) {
}
