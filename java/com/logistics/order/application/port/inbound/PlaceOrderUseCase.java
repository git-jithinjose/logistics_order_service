package com.logistics.order.application.port.inbound;

import org.springframework.stereotype.Service;

import com.logistics.order.application.port.inbound.dto.OrderResponseDto;
import com.logistics.order.application.port.inbound.dto.PlaceOrderCommand;

@Service
public interface PlaceOrderUseCase {
	OrderResponseDto placeOrder(PlaceOrderCommand command);
}
