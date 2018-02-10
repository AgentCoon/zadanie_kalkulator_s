package com.agentcoon.incomecalculator.domain.calculator;

import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.CountryRepository;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.Money;
import com.agentcoon.incomecalculator.domain.currencyconverter.CurrencyConverter;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MonthlyNetIncomeCalculator {

    @Value("${daysInMonth}")
    private int daysInMonth;

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
        BigDecimal monthlyIncome = dailyRate.multiply(BigDecimal.valueOf(daysInMonth));
        BigDecimal monthlyTaxableIncome = BigDecimal.valueOf(Math.max(0.0f, monthlyIncome.subtract(country.getFixedCostValue()).floatValue()));
        BigDecimal tax = monthlyTaxableIncome.multiply(country.getTaxRateValue());

        return monthlyIncome.subtract(tax);
    }
}
