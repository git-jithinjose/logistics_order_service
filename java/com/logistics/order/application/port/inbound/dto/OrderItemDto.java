package com.logistics.order.application.port.inbound.dto;

public record OrderItemDto(

		String productId, String productName, Integer quantity) {

}
