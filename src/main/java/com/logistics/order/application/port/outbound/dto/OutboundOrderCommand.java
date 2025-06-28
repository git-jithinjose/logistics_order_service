package com.logistics.order.application.port.outbound.dto;

import java.util.List;

public record OutboundOrderCommand(

		String createdAt, String status, String orderNumber, String shippingAddressId, List<OutboundOrderItem> items,
		Long customerId) {

}
