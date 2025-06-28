package com.logistics.order.adapter.outbound.memory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.logistics.order.adapter.outbound.dto.ProductPrice;
import com.logistics.order.application.port.outbound.ProductReadPort;

@Repository
public class InMemoryProductPriceAdapter implements ProductReadPort {

	private final Map<String, List<ProductPrice>> priceMap = Map.of("P001", List.of(
			new ProductPrice("P001", new BigDecimal("100.00"), Currency.getInstance("USD"), LocalDate.of(2025, 6, 1),
					LocalDate.of(2025, 6, 30)),
			new ProductPrice("P001", new BigDecimal("120.00"), Currency.getInstance("USD"), LocalDate.of(2025, 7, 1),
					null),
			new ProductPrice(
					"P001", new BigDecimal("92.00"), Currency.getInstance("EUR"), LocalDate.of(2025, 6, 1), null)),
			"P002",
			List.of(new ProductPrice("P002", new BigDecimal("80.00"), Currency.getInstance("USD"),
					LocalDate.of(2025, 6, 1), null),
					new ProductPrice("P002", new BigDecimal("75.00"), Currency.getInstance("EUR"),
							LocalDate.of(2025, 6, 1), null)));

	@Override
	public Optional<ProductPrice> getCurrentPrice(String productId, LocalDate date, Currency currency) {
		List<ProductPrice> prices = priceMap.get(productId);
		if (prices == null)
			return Optional.empty();

		return prices.stream().filter(p -> p.getCurrency().equals(currency)).filter(
				p -> !date.isBefore(p.getValidFrom()) && (p.getValidTo() == null || !date.isAfter(p.getValidTo())))
				.findFirst();
	}
}