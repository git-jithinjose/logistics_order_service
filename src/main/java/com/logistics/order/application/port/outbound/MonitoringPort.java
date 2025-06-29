package com.logistics.order.application.port.outbound;

public interface MonitoringPort {
	void recordOrderInitiated();
	void recordOrderApproved();
    void recordOrderCancelled();

}
