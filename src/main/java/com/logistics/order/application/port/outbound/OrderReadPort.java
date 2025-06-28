package com.logistics.order.application.port.outbound;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logistics.order.application.port.outbound.dto.OutboundOrderCommand;


@Service
public interface OrderReadPort {
    Optional<OutboundOrderCommand> loadOrder(String id);
    long getSequence();

}
