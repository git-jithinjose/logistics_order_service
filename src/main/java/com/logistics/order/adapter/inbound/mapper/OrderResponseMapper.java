package com.logistics.order.adapter.inbound.mapper;

import com.logistics.order.adapter.inbound.dto.OrderResponse;
import com.logistics.order.application.port.inbound.dto.OrderResponseDto;

public class OrderResponseMapper {


	public static OrderResponse toOrderResponse (OrderResponseDto dto) {
        if (dto == null) return null;
        return new OrderResponse(dto.orderNumber(), dto.status());
    }

    public static OrderResponseDto toOrder(OrderResponse response) {
        if (response == null) return null;
        return new OrderResponseDto(response.orderNumber(), response.status());
    }
}
