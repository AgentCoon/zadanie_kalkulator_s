package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;

import java.math.BigDecimal;

public interface ExchangeRateProvider {

    BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) throws ExchangeRateNotFoundException;
}
