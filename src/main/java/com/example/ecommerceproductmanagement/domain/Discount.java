package com.example.ecommerceproductmanagement.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Discount {
    private final BigDecimal value;
    private final DiscountType type;

    public enum DiscountType {
        PERCENTAGE, FLAT
    }

    public Discount(BigDecimal value, DiscountType type) {
        validateDiscount(value, type);
        this.value = value;
        this.type = type;
    }

    private void validateDiscount(BigDecimal value, DiscountType type) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount must be positive");
        }
        if (type == DiscountType.PERCENTAGE && value.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage discount cannot exceed 100%");
        }
    }

    public BigDecimal apply(BigDecimal price) {
        if (type == DiscountType.PERCENTAGE) {
            return price.subtract(price.multiply(value.divide(BigDecimal.valueOf(100))));
        } else {
            return price.subtract(value).max(BigDecimal.ZERO);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public DiscountType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(value, discount.value) && type == discount.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}

