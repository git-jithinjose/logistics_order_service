package com.logistics.order.adapter.outbound.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class ProductPrice {
    private final String productId;
    private final BigDecimal price;
    private final Currency currency;
    private final LocalDate validFrom;
    private final LocalDate validTo;

    public ProductPrice(String productId, BigDecimal price, Currency currency, LocalDate validFrom, LocalDate validTo) {
        this.productId = productId;
        this.price = price;
        this.currency = currency;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }
}


