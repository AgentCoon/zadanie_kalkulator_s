package com.agentcoon.incomecalculator.domain;

public class Currency {

    private final String currencyCode;

    private Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public static class Builder {
        private String currencyCode;

        public Builder withCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Currency build() {
            return new Currency(currencyCode);
        }
    }
}
