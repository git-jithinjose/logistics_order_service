package com.logistics.order.adapter.outbound;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.logistics.order.application.port.outbound.OrderCommandPort;
import com.logistics.order.application.port.outbound.OrderReadPort;
import com.logistics.order.application.port.outbound.dto.OutboundOrderCommand;

@Repository
public class InMemoryOrderRepositoryAdapter implements OrderCommandPort, OrderReadPort{

 private final Map<String, OutboundOrderCommand> store = new ConcurrentHashMap<>();
 private final AtomicLong sequence = new AtomicLong(0);

 @Override
 public OutboundOrderCommand persistOrder(OutboundOrderCommand order) {
     store.put(order.orderNumber(), order);
     return order;
 }

 @Override
 public Optional<OutboundOrderCommand> loadOrder(String orderNumber) {
     return Optional.ofNullable(store.get(orderNumber));
 }
 @Override
 public long getSequence() {
     return sequence.incrementAndGet();
 }

}

