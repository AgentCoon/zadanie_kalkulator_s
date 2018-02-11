package com.agentcoon.incomecalculator.exchangerate.client.api;

import java.math.BigDecimal;

public class ExchangeRateDto {

    private String baseCurrency;
    private String currency;
    private BigDecimal conversionRate;

    public ExchangeRateDto(String baseCurrency, String currency, BigDecimal conversionRate) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.conversionRate = conversionRate;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }
}
