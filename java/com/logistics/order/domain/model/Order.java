package com.logistics.order.domain.model;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.logistics.order.domain.exception.DomainException;

public class Order {

	public enum Status {
		PENDING, APPROVED, CANCELLED
	}

	private Instant createdAt;
	private Status status;
	private OrderNumber orderNumber;
	private List<OrderItem> items;
	private String shippingAddressId;
	private Long customerId;

	public Order(List<OrderItem> items, OrderNumber orderNumber, Long customerId, String shippingAddressId) {
		if (items == null || items.isEmpty()) {
			throw new DomainException("Order must have at least one item.");
		}
		this.orderNumber = orderNumber;
		this.createdAt = Instant.now();
		this.status = Status.PENDING;
		this.items = Collections.unmodifiableList(items);
		this.shippingAddressId = shippingAddressId;
		this.customerId = customerId;

	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Status getStatus() {
		return status;
	}

	public OrderNumber getOrderNumber() {
		return orderNumber;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public String getShippingAddressId() {
		return shippingAddressId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void approve() {
		if (status != Status.PENDING) {
			throw new DomainException("Only pending orders can be approved.");
		}
		this.status = Status.APPROVED;
	}

	public void cancel() {
		if (status != Status.PENDING) {
			throw new DomainException("Only pending orders can be cancelled.");
		}
		this.status = Status.CANCELLED;
	}

	public static Order restore(List<OrderItem> items, OrderNumber orderNumber, Long customerId,
			String shippingAddressId, Instant createdAt, Status status) {
		Order order = new Order(items, orderNumber, customerId, shippingAddressId);
		order.status = status;
		order.createdAt = createdAt;
		return order;
	}

}
