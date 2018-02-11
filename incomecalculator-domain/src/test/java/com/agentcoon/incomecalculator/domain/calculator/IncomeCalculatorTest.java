package com.agentcoon.incomecalculator.domain.calculator;

import com.agentcoon.incomecalculator.domain.*;
import com.agentcoon.incomecalculator.domain.currencyconverter.CurrencyConverter;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class IncomeCalculatorTest {

    private CountryRepository countryRepository;
    private CurrencyConverter currencyConverter;
    private MonthlyNetIncomeCalculator monthlyNetIncomeCalculator;

    private IncomeCalculator incomeCalculator;

    @Before
    public void setUp() {
        countryRepository = mock(CountryRepository.class);
        currencyConverter = mock(CurrencyConverter.class);
        monthlyNetIncomeCalculator = mock(MonthlyNetIncomeCalculator.class);

        incomeCalculator = new IncomeCalculator(countryRepository, currencyConverter, monthlyNetIncomeCalculator);
    }

    @Test
    public void itCalculatesNetIncomeForSameCurrency() throws NotFoundException {
        String currencyCode = "PLN";
        String countryCode = "PL";
        Currency currency = new Currency(currencyCode);

        Country country = new Country.Builder()
                .withCountryCode(countryCode)
                .withCurrency(currency).build();

        BigDecimal dailyRate = BigDecimal.valueOf(200);
        BigDecimal expectedNetIncome = BigDecimal.valueOf(4500.20);

        when(countryRepository.findOneByCountryCode(countryCode)).thenReturn(country);
        when(monthlyNetIncomeCalculator.calculateNetMonthlyIncome(dailyRate, country)).thenReturn(expectedNetIncome);

        Money result = incomeCalculator.calculate(dailyRate, countryCode, currency);

        assertEquals(expectedNetIncome, result.getAmount());
        verify(countryRepository, times(1)).findOneByCountryCode(countryCode);
        verify(monthlyNetIncomeCalculator, times(1)).calculateNetMonthlyIncome(dailyRate, country);
        verify(currencyConverter, never()).convert(any(), any(), any());
    }

    @Test
    public void itCalculatesNetIncomeWithCurrencyConversion() throws NotFoundException {
        String plCurrencyCode = "PLN";
        String plCountryCode = "PL";
        Currency plCurrency = new Currency(plCurrencyCode);

        String gbpCurrencyCode = "GBP";
        Currency ukCurrency = new Currency(gbpCurrencyCode);

        Country country = new Country.Builder()
                .withCountryCode(plCountryCode)
                .withCurrency(plCurrency).build();

        BigDecimal dailyRate = BigDecimal.valueOf(200);
        BigDecimal expectedNetIncome = BigDecimal.valueOf(4500.20);
        BigDecimal expectedConvertedNetIncome = BigDecimal.valueOf(1000.20);

        when(countryRepository.findOneByCountryCode(plCountryCode)).thenReturn(country);
        when(monthlyNetIncomeCalculator.calculateNetMonthlyIncome(dailyRate, country)).thenReturn(expectedNetIncome);
        when(currencyConverter.convert(plCurrency, ukCurrency, expectedNetIncome)).thenReturn(expectedConvertedNetIncome);

        Money result = incomeCalculator.calculate(dailyRate, plCountryCode, ukCurrency);

        assertEquals(expectedConvertedNetIncome, result.getAmount());
        verify(countryRepository, times(1)).findOneByCountryCode(plCountryCode);
        verify(monthlyNetIncomeCalculator, times(1)).calculateNetMonthlyIncome(dailyRate, country);
        verify(currencyConverter, times(1)).convert(plCurrency, ukCurrency, expectedNetIncome);
    }

    @Test
            (expected=NotFoundException.class)
    public void itCalculatesNetIncomeWhenCountryNotFound() throws NotFoundException {
        String currencyCode = "PLN";
        String countryCode = "PL";
        Currency currency = new Currency(currencyCode);

        BigDecimal dailyRate = BigDecimal.valueOf(200);

        doThrow(NotFoundException.class).when(countryRepository).findOneByCountryCode(countryCode);

        incomeCalculator.calculate(dailyRate, countryCode, currency);

        verify(countryRepository, times(1)).findOneByCountryCode(countryCode);
        verify(monthlyNetIncomeCalculator, never()).calculateNetMonthlyIncome(any(), any());
        verify(currencyConverter, never()).convert(any(), any(), any());
    }
}
