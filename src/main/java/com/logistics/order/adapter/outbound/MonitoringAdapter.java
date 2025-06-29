package com.logistics.order.adapter.outbound;

import org.springframework.stereotype.Component;

import com.logistics.order.application.port.outbound.MonitoringPort;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class MonitoringAdapter implements MonitoringPort {

    private final Counter initiatedOrdersCounter;
    private final Counter approvedOrdersCounter;
    private final Counter cancelledOrdersCounter;

    public MonitoringAdapter(MeterRegistry registry) {
        this.initiatedOrdersCounter = Counter.builder("orders.initiated").register(registry);
        this.approvedOrdersCounter = Counter.builder("orders.approved").register(registry);
        this.cancelledOrdersCounter = Counter.builder("orders.cancelled").register(registry);
    }
    

    @Override
    public void recordOrderInitiated() {
    	initiatedOrdersCounter.increment();
    }

    @Override
    public void recordOrderApproved() {
    	approvedOrdersCounter.increment();
    }


    @Override
    public void recordOrderCancelled() {
    	cancelledOrdersCounter.increment();
    }
}