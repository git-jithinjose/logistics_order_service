package com.logistics.order.application.port.outbound.dto;

public record OutboundOrderItem(String productId, Integer quantity) {
}