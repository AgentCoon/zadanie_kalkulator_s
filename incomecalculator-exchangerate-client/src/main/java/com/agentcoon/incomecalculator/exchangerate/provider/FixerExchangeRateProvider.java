package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateApiDto;
import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.exchangerate.provider.fixer.FixerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class FixerExchangeRateProvider implements ExchangeRateProvider {

    private final FixerGateway fixerGateway;

    @Autowired
    public FixerExchangeRateProvider(FixerGateway fixerGateway) {
        this.fixerGateway = fixerGateway;
    }

    @Override
    public List<ExchangeRateDto> getExchangeRates(String baseCurrency) {

        ExchangeRateApiDto dto = fixerGateway.fetchExchangeRates(baseCurrency);

        return dto.toFlatList();
    }

    @Override
    public ExchangeRateDto getExchangeRate(String baseCurrency, String targetCurrency) throws ExchangeRateNotFoundException {

        ExchangeRateApiDto dto = fixerGateway.fetchExchangeRates(baseCurrency);

        BigDecimal rate = dto.getRates().get(targetCurrency);

        if (rate == null) {
            throw new ExchangeRateNotFoundException("Exchange rate from " + baseCurrency +
                    " to " + targetCurrency + " not found.");
        }

        return new ExchangeRateDto(baseCurrency, targetCurrency, rate);
    }
}
