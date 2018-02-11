package com.agentcoon.incomecalculator.exchangerate.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateApiDto {

    private String base;
    private Map<String, BigDecimal> rates;

    public ExchangeRateApiDto() {}

    public ExchangeRateApiDto(String base, Map<String, BigDecimal> rates) {
        this.base = base;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public List<ExchangeRateDto> toFlatList() {
        return rates.entrySet().stream()
                .map(e -> new ExchangeRateDto(base, e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public static final class Builder {

        private String baseCurrency;
        private Map<String, BigDecimal> rates;

        public Builder withBaseCurrency(String baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }
        public Builder withRates(Map<String, BigDecimal> rates) {
            this.rates = rates;
            return this;
        }

        public ExchangeRateApiDto build() {
            return new ExchangeRateApiDto(baseCurrency, rates);
        }
    }
}
