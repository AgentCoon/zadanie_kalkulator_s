package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateApiDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.exchangerate.provider.fixer.FixerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("fixerExchangeRateProvider")
public class FixerExchangeRateProvider implements ExchangeRateProvider {

    private final FixerGateway fixerGateway;

    @Autowired
    public FixerExchangeRateProvider(FixerGateway fixerGateway) {
        this.fixerGateway = fixerGateway;
    }

    @Override
    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) throws ExchangeRateNotFoundException {

        ExchangeRateApiDto dto = fixerGateway.fetchExchangeRates(sourceCurrency);

        BigDecimal rate = dto.getRates().get(targetCurrency);

        if (rate == null) {
            throw new ExchangeRateNotFoundException("Exchange rate from " + sourceCurrency +
                    " to " + targetCurrency + " not found.");
        }

        return rate;
    }
}
