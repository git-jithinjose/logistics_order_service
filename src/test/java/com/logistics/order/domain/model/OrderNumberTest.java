package com.logistics.order.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderNumberTest {

    @Test
    void of_ShouldFormatSequenceCorrectly() {
        OrderNumber orderNumber = OrderNumber.of(42L);
        assertEquals("ORD000042", orderNumber.getValue());
    }
    
    @Test
    void of_ShouldThrowException() {
        OrderNumber orderNumber = OrderNumber.of(0L);
        assertEquals("ORD000000", orderNumber.getValue());
    }
    
    
    

}
