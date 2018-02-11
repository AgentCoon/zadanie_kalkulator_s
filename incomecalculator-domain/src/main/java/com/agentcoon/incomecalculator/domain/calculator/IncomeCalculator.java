package com.agentcoon.incomecalculator.domain.calculator;

import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.CountryRepository;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.Money;
import com.agentcoon.incomecalculator.domain.currencyconverter.CurrencyConverter;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IncomeCalculator {

    private final CountryRepository countryRepository;
    private final CurrencyConverter currencyConverter;
    private final MonthlyNetIncomeCalculator monthlyNetIncomeCalculator;

    @Autowired
    public IncomeCalculator(CountryRepository countryRepository,
                            CurrencyConverter currencyConverter,
                            MonthlyNetIncomeCalculator monthlyNetIncomeCalculator) {
        this.countryRepository = countryRepository;
        this.currencyConverter = currencyConverter;
        this.monthlyNetIncomeCalculator = monthlyNetIncomeCalculator;
    }

    public Money calculate(BigDecimal dailyRate, String countryCode, Currency targetCurrency) throws NotFoundException {

        Country country = countryRepository.findOneByCountryCode(countryCode);

        BigDecimal netMonthlyIncome = monthlyNetIncomeCalculator.calculate(dailyRate, country);

        if (country.currencyIsTheSameAs(targetCurrency)) {
            return new Money(netMonthlyIncome, targetCurrency);
        }

        BigDecimal convertedAmount = currencyConverter.convert(country.getCurrency(), targetCurrency, netMonthlyIncome);

        return new Money(convertedAmount, targetCurrency);
    }
}
