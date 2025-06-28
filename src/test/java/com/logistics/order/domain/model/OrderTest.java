package com.logistics.order.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.logistics.order.domain.model.Order.Status;

class OrderTest {

	
	private final OrderNumber testOrderNumber = new OrderNumber("ORD123");
    private final Long testCustomerId = 1L;
    private final String testAddress = "addr1";
    private final OrderItem testItem = new OrderItem(new ProductId("prod1"), 2);
	
    
    private Order createBasicOrder() {
        return new Order(List.of(testItem), testOrderNumber, testCustomerId, testAddress);
    }
    
    @Test
    @DisplayName("Should create order with status INITIATED")
    void testCreateOrder() {
        Order order = createBasicOrder();

        assertEquals(Order.Status.INITIATED, order.getStatus());
        assertEquals("ORD123", order.getOrderNumber().getValue());
        assertEquals(1, order.getItems().size());
    }

    
    @Test
    void approve_ShouldChangeStatusToApproved() {
        Order order = createBasicOrder();
        order.approve();
        assertEquals(Order.Status.APPROVED, order.getStatus());
    }
    @Test
    void cancel_ShouldChangeStatusToCaneclled() {
        Order order = createBasicOrder();
        order.cancel();
        assertEquals(Order.Status.CANCELLED, order.getStatus());
    }
}
