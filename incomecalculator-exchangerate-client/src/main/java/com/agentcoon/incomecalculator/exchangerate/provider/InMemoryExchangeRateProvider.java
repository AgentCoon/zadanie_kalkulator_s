package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class InMemoryExchangeRateProvider implements ExchangeRateProvider {

    private List<ExchangeRateDto> exchangeRates = new ArrayList<>();

    public InMemoryExchangeRateProvider() {
        exchangeRates.add(new ExchangeRateDto("PLN", "PLN", BigDecimal.ONE));
        exchangeRates.add(new ExchangeRateDto("PLN", "EUR", BigDecimal.valueOf(0.24)));
        exchangeRates.add(new ExchangeRateDto("PLN", "GBP", BigDecimal.valueOf(0.21)));
    }

    @Override
    public List<ExchangeRateDto> getExchangeRates(String baseCurrency) {
        return exchangeRates.stream()
                .filter(e -> e.getBaseCurrency().equals(baseCurrency))
                .collect(toList());
    }

    @Override
    public ExchangeRateDto getExchangeRate(String baseCurrency, String targetCurrency) throws ExchangeRateNotFoundException {
        return exchangeRates.stream()
                .filter(e -> e.getCurrency().equals(targetCurrency) &&
                        e.getBaseCurrency().equals(baseCurrency)).findFirst()
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate from " + baseCurrency +
                        " to " + targetCurrency + " not found."));
    }
}
