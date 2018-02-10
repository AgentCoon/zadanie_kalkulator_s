package com.agentcoon.incomecalculator.domain.currencyconverter;

import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;

import java.math.BigDecimal;

public interface CurrencyConverter {

    BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) throws NotFoundException;
}
