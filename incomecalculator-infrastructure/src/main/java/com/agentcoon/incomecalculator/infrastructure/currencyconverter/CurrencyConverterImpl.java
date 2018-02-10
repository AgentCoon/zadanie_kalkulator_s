package com.agentcoon.incomecalculator.infrastructure.currencyconverter;

import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.currencyconverter.CurrencyConverter;
import com.agentcoon.incomecalculator.domain.ExchangeRate;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProvider;
import com.agentcoon.incomecalculator.infrastructure.exchangerate.ExchangeRateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {

    private final ExchangeRateProvider exchangeRatesProvider;
    private final ExchangeRateDtoMapper exchangeRateDtoMapper;

    @Autowired
    public CurrencyConverterImpl(@Qualifier("inMemoryExchangeRateProvider") ExchangeRateProvider exchangeRatesProvider,
                                 ExchangeRateDtoMapper exchangeRateDtoMapper) {
        this.exchangeRatesProvider = exchangeRatesProvider;
        this.exchangeRateDtoMapper = exchangeRateDtoMapper;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) throws NotFoundException {

        try {
            ExchangeRateDto exchangeRateDto = exchangeRatesProvider.getExchangeRate(targetCurrency.getCurrencyCode(), sourceCurrency.getCurrencyCode());
            ExchangeRate exchangeRate = exchangeRateDtoMapper.from(exchangeRateDto);

            return amount.multiply(exchangeRate.getConversionRate());
        } catch (ExchangeRateNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
