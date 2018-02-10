package com.agentcoon.incomecalculator.infrastructure.exchangerate;

import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.ExchangeRate;
import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateApiDto;
import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeRateDtoMapper {

    public List<ExchangeRate> from(ExchangeRateApiDto exchangeRateDto) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        String baseCurrency = exchangeRateDto.getBase();

        exchangeRateDto.getRates().forEach((key, value) -> exchangeRates.add(new ExchangeRate.Builder()
                .withBaseCurrency(new Currency(baseCurrency))
                .withCurrency(new Currency(key))
                .withConversionRate(value)
                .build()));

        return exchangeRates;
    }

    public ExchangeRate from(ExchangeRateDto dto) {
        return new ExchangeRate(new Currency(dto.getBaseCurrency()),
                new Currency(dto.getCurrency()),
                dto.getConversionRate());
    }
}
