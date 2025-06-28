package com.logistics.order.application.mapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.logistics.order.application.port.outbound.dto.OutboundOrderCommand;
import com.logistics.order.application.port.outbound.dto.OutboundOrderItem;
import com.logistics.order.domain.model.Order;
import com.logistics.order.domain.model.OrderItem;
import com.logistics.order.domain.model.OrderNumber;
import com.logistics.order.domain.model.ProductId;



public class OrderToOutboundOrderMapper {

    public static OutboundOrderCommand toOutboundOrderCommand(Order order) {
        List<OutboundOrderItem> items = order.getItems().stream()
            .map(item -> new OutboundOrderItem(
                item.getProductId().toString(),
                item.getQuantity()))
            .collect(Collectors.toList());

        return new OutboundOrderCommand(
            order.getCreatedAt().toString(),
            order.getStatus().name(),
            order.getOrderNumber().getValue(),
            order.getShippingAddressId(),
            items,
            order.getCustomerId()
        );
    }

    public static Order toDomainOrder(OutboundOrderCommand command) {
        List<OrderItem> items = command.items().stream()
            .map(i -> new OrderItem(new ProductId(i.productId()), i.quantity()))
            .collect(Collectors.toList());

        return Order.restore(
            items,
            new OrderNumber(command.orderNumber()),
            command.customerId(),
            command.shippingAddressId(),
            Instant.parse(command.createdAt()),
            Order.Status.valueOf(command.status())
        );
    }
}




