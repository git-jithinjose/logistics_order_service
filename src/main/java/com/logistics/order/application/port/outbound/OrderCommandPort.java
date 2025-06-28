package com.logistics.order.application.port.outbound;



import org.springframework.stereotype.Service;

import com.logistics.order.application.port.outbound.dto.OutboundOrderCommand;

@Service
public interface OrderCommandPort {
	OutboundOrderCommand persistOrder(OutboundOrderCommand order);
}
