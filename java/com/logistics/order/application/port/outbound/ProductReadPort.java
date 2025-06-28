package com.logistics.order.application.port.outbound;



import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logistics.order.adapter.outbound.dto.ProductPrice;

@Service
public interface ProductReadPort {
    Optional<ProductPrice> getCurrentPrice(String productId, LocalDate date, Currency currency);
}
