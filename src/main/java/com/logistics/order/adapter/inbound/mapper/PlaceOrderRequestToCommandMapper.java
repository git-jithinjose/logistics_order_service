package com.logistics.order.adapter.inbound.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.logistics.order.adapter.inbound.dto.PlaceOrderRequest;
import com.logistics.order.application.port.inbound.dto.OrderItemCommand;
import com.logistics.order.application.port.inbound.dto.PlaceOrderCommand;

@Component
public class PlaceOrderRequestToCommandMapper {
	public PlaceOrderCommand map(PlaceOrderRequest request) {
		return new PlaceOrderCommand(request.items().stream()
				.map(i -> new OrderItemCommand(i.productId(), i.quantity())).collect(Collectors.toList()),request.customerId(),request.shippingAddressId());
	}
}
