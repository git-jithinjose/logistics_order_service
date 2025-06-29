package com.logistics.order.application.port.outbound;

public interface MonitoringPort {
	
	void incrementApprovedOrders();
    void incrementCancelledOrders();

}
