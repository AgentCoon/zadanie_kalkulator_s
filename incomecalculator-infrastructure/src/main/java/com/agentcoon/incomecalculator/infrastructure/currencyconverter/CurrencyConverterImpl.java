package com.agentcoon.incomecalculator.infrastructure.currencyconverter;

import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.currencyconverter.CurrencyConverter;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.infrastructure.exchangerate.ExchangeRateGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {

    private final ExchangeRateGateway exchangeRateGateway;

    @Autowired
    public CurrencyConverterImpl(ExchangeRateGateway exchangeRateGateway) {
        this.exchangeRateGateway = exchangeRateGateway;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) throws NotFoundException {

        try {
            BigDecimal rate = exchangeRateGateway.getExchangeRate(sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());

            return amount.multiply(rate);
        } catch (ExchangeRateNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
