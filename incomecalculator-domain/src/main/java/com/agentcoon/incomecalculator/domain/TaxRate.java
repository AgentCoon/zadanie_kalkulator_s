package com.agentcoon.incomecalculator.domain;

public class TaxRate {

    private final Float rate;
    private final Float fixedCost;

    private TaxRate(Float rate, Float fixedCost) {
        this.rate = rate;
        this.fixedCost = fixedCost;
    }

    public Float getRate() {
        return rate;
    }

    public Float getFixedCost() {
        return fixedCost;
    }

    public static class Builder {
        private Float rate;
        private Float fixedCost;

        public Builder withRate(Float rate) {
            this.rate = rate;
            return this;
        }

        public Builder withFixedCost(Float fixedCost) {
            this.fixedCost = fixedCost;
            return this;
        }

        public TaxRate build() {
            return new TaxRate(rate, fixedCost);
        }
    }
}
