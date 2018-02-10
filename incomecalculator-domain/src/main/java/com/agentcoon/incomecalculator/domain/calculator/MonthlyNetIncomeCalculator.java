package com.agentcoon.incomecalculator.domain.calculator;

import com.agentcoon.incomecalculator.domain.*;
import com.agentcoon.incomecalculator.domain.currencyconverter.CurrencyConverter;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MonthlyNetIncomeCalculator {

    private static final int DAYS_IN_MONTH = 22;

    private final CountryRepository countryRepository;
    private final CurrencyConverter currencyConverter;

    @Autowired
    public MonthlyNetIncomeCalculator(CountryRepository countryRepository, CurrencyConverter currencyConverter) {
        this.countryRepository = countryRepository;
        this.currencyConverter = currencyConverter;
    }

    public Money calculate(BigDecimal dailyRate, String countryCode, Currency targetCurrency) throws NotFoundException {

        Country country = countryRepository.findOneByCountryCode(countryCode);

        BigDecimal netMonthlyIncome = calculateNetMonthlyIncome(dailyRate, country);

        if (country.currencyIsTheSameAs(targetCurrency)) {
            return new Money(netMonthlyIncome, targetCurrency);
        }

        BigDecimal convertedAmount = currencyConverter.convert(country.getCurrency(), targetCurrency, netMonthlyIncome);

        return new Money(convertedAmount, targetCurrency);
    }

    private BigDecimal calculateNetMonthlyIncome(BigDecimal dailyRate, Country country) {
        BigDecimal monthlyIncome = dailyRate.multiply(BigDecimal.valueOf(DAYS_IN_MONTH));
        BigDecimal monthlyTaxableIncome = monthlyIncome.subtract(country.getFixedCostValue());
        BigDecimal tax = monthlyTaxableIncome.multiply(country.getTaxRateValue());

        return monthlyIncome.subtract(tax);
    }
}
