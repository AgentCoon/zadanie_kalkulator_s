package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;

import java.util.List;

public interface ExchangeRateProvider {

    List<ExchangeRateDto> getExchangeRates();

    ExchangeRateDto getExchangeRate(String baseCurrency, String targetCurrency) throws ExchangeRateNotFoundException;
}
