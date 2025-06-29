package com.logistics.order.adapter.outbound.memory;

import org.springframework.stereotype.Component;

import com.logistics.order.application.port.outbound.MonitoringPort;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class MonitoringAdapter implements MonitoringPort {

    private final Counter approvedOrdersCounter;
    private final Counter cancelledOrdersCounter;

    public MonitoringAdapter(MeterRegistry registry) {
        this.approvedOrdersCounter = Counter.builder("orders.approved").register(registry);
        this.cancelledOrdersCounter = Counter.builder("orders.cancelled").register(registry);
    }

    @Override
    public void incrementApprovedOrders() {
        approvedOrdersCounter.increment();
    }

    @Override
    public void incrementCancelledOrders() {
        cancelledOrdersCounter.increment();
    }
}