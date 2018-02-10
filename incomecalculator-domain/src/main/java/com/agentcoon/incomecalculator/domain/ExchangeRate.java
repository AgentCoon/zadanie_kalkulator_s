package com.agentcoon.incomecalculator.domain;

import java.math.BigDecimal;

public class ExchangeRate {

    private final Currency baseCurrency;
    private final Currency currency;
    private final BigDecimal conversionRate;

    public ExchangeRate(Currency baseCurrency, Currency currency, BigDecimal conversionRate) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.conversionRate = conversionRate;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public static class Builder {
        private Currency baseCurrency;
        private Currency currency;
        private BigDecimal conversionRate;

        public Builder withBaseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder withConversionRate(BigDecimal conversionRate) {
            this.conversionRate = conversionRate;
            return this;
        }

        public ExchangeRate build() {
            return new ExchangeRate(baseCurrency, currency, conversionRate);
        }
    }
}
