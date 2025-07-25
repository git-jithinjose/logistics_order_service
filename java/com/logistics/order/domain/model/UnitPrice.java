package com.logistics.order.domain.model;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class UnitPrice {
    private final BigDecimal amount;
    private final Currency currency;

    public UnitPrice(BigDecimal amount, Currency currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and Currency cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public UnitPrice add(UnitPrice other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new UnitPrice(amount.add(other.amount), currency);
    }

    public static UnitPrice zero(Currency currency) {
        return new UnitPrice(BigDecimal.ZERO, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitPrice)) return false;
        UnitPrice money = (UnitPrice) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return currency + " " + amount;
    }
    public UnitPrice multiply(int multiplier) {
        return new UnitPrice(amount.multiply(BigDecimal.valueOf(multiplier)), currency);
    }

}
