package com.logistics.order.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.logistics.order.adapter.inbound.web.OrderController;
import com.logistics.order.application.exception.ApplicationException;
import com.logistics.order.application.exception.UnexpectedOrderServiceException;
import com.logistics.order.application.mapper.OrderToOutboundOrderMapper;
import com.logistics.order.application.port.inbound.ApproveOrderUseCase;
import com.logistics.order.application.port.inbound.CancelOrderUseCase;
import com.logistics.order.application.port.inbound.PlaceOrderUseCase;
import com.logistics.order.application.port.inbound.QueryOrderUseCase;
import com.logistics.order.application.port.inbound.dto.OrderResponseDto;
import com.logistics.order.application.port.inbound.dto.PlaceOrderCommand;
import com.logistics.order.application.port.outbound.MonitoringPort;
import com.logistics.order.application.port.outbound.OrderCommandPort;
import com.logistics.order.application.port.outbound.OrderReadPort;
import com.logistics.order.application.port.outbound.dto.OutboundOrderCommand;
import com.logistics.order.domain.model.Order;
import com.logistics.order.domain.model.OrderItem;
import com.logistics.order.domain.model.OrderNumber;
import com.logistics.order.domain.model.ProductId;

@Service
public class OrderService implements PlaceOrderUseCase, ApproveOrderUseCase, CancelOrderUseCase, QueryOrderUseCase {

	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	private final OrderCommandPort orderWritePort;
	private final OrderReadPort orderReadPort;
    private final MonitoringPort monitoringPort;

	public OrderService(OrderCommandPort orderWritePort, OrderReadPort orderReadPort,MonitoringPort monitoringPort) {
		this.orderWritePort = orderWritePort;
		this.orderReadPort = orderReadPort;
		this.monitoringPort = monitoringPort;
	}

	@Override
	public OrderResponseDto placeOrder(PlaceOrderCommand command) {
		LOG.info("Received place order request");
		List<OrderItem> orderItems = command.items().stream().map(itemCmd -> {
			ProductId productId = new ProductId(itemCmd.productId());
			return new OrderItem(productId, itemCmd.quantity());
		}).collect(Collectors.toList());

		OrderNumber orderNumber = OrderNumber.of(orderReadPort.getSequence());
		// Create Order aggregate // Builder if required (Number of parameters are more)
		Order order = Order.initiate(orderItems, orderNumber, command.customerId(), command.shippingAddressId());

		OutboundOrderCommand savedOrder = orderWritePort
				.persistOrder(OrderToOutboundOrderMapper.toOutboundOrderCommand(order));

		return new OrderResponseDto(savedOrder.orderNumber().toString(), savedOrder.status());
	}

	@Override
	public OrderResponseDto approveOrder(String orderNumber) {
		try {
			LOG.info("Received approveOrder request");
			Order order = OrderToOutboundOrderMapper
					.toDomainOrder(orderReadPort.loadOrder(orderNumber).orElseThrow(() -> {
						return new ApplicationException(
								"Order cannot be approved because the order number was not found.");
					}));

			order.approve();

			OutboundOrderCommand updatedOrder = orderWritePort
					.persistOrder(OrderToOutboundOrderMapper.toOutboundOrderCommand(order));

		    monitoringPort.incrementApprovedOrders();
			return new OrderResponseDto(updatedOrder.orderNumber(), updatedOrder.status());

		} catch (ApplicationException ex) {
			LOG.warn("order : {} not found while calling loadOrder", orderNumber);
			throw ex;
		} catch (Exception ex) {
			throw new UnexpectedOrderServiceException(
					String.format("Unexpected errors while approving the order %s: %s", orderNumber, ex.getMessage()),
					ex);
		}
	}

	@Override
	public OrderResponseDto cancelOrder(String orderNumber) {
		LOG.info("Received cancelOrder request");
		Order order = OrderToOutboundOrderMapper.toDomainOrder(orderReadPort.loadOrder(orderNumber).orElseThrow(() -> {
			LOG.warn("order : {} not found while calling loadOrder", orderNumber);
			return new ApplicationException("Order not found");
		}));

		order.cancel();

		OutboundOrderCommand updatedOrder = orderWritePort
				.persistOrder(OrderToOutboundOrderMapper.toOutboundOrderCommand(order));
		 monitoringPort.incrementCancelledOrders();
		return new OrderResponseDto(updatedOrder.orderNumber(), updatedOrder.status());

	}

	@Override
	public OrderResponseDto getOrderStatus(String orderNumber) {
		LOG.info("Received getOrderStatus request");
		OutboundOrderCommand orderdetails = orderReadPort.loadOrder(orderNumber).orElseThrow(() -> {
			LOG.warn("order : {} not found while calling loadOrder", orderNumber);
			return new ApplicationException("Order not found");
		});

		return new OrderResponseDto(orderdetails.orderNumber(), orderdetails.status());

	}
}
