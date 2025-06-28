package com.logistics.order.adapter.inbound.dto;

import java.math.BigDecimal;


public record OrderItemEnrichedResponse(
	    String productId,
	    Integer quantity,
	    String productName,
	    BigDecimal price,
	    BigDecimal itemTotal

	) {

	}
