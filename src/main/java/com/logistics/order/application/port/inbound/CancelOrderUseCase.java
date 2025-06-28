package com.logistics.order.application.port.inbound;

import org.springframework.stereotype.Service;

import com.logistics.order.application.port.inbound.dto.OrderResponseDto;

@Service
public interface CancelOrderUseCase {
	OrderResponseDto cancelOrder(String orderId);
}