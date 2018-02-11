package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component("inMemoryExchangeRateProvider")
public class InMemoryExchangeRateProvider implements ExchangeRateProvider {

    private List<ExchangeRateDto> exchangeRates = new ArrayList<>();

    public InMemoryExchangeRateProvider() {
        exchangeRates.add(new ExchangeRateDto("PLN", "PLN", BigDecimal.ONE));
        exchangeRates.add(new ExchangeRateDto("EUR", "PLN", BigDecimal.valueOf(4.3)));
        exchangeRates.add(new ExchangeRateDto("GBP", "PLN", BigDecimal.valueOf(4.7)));
    }

    @Override
    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) throws ExchangeRateNotFoundException {
        ExchangeRateDto dto = exchangeRates.stream()
                .filter(e -> e.getCurrency().equals(targetCurrency) &&
                        e.getBaseCurrency().equals(sourceCurrency)).findFirst()
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate from " + sourceCurrency +
                        " to " + targetCurrency + " not found."));

        return dto.getConversionRate();
    }
}
