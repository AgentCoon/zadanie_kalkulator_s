package com.agentcoon.incomecalculator.domain;

import java.math.BigDecimal;

public class TaxRate {

    private final BigDecimal rate;
    private final BigDecimal fixedCost;

    private TaxRate(BigDecimal rate, BigDecimal fixedCost) {
        this.rate = rate;
        this.fixedCost = fixedCost;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getFixedCost() {
        return fixedCost;
    }

    public static class Builder {
        private BigDecimal rate;
        private BigDecimal fixedCost;

        public Builder withRate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        public Builder withFixedCost(BigDecimal fixedCost) {
            this.fixedCost = fixedCost;
            return this;
        }

        public TaxRate build() {
            return new TaxRate(rate, fixedCost);
        }
    }
}
