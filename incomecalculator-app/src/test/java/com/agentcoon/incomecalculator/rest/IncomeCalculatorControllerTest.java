package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.IncomeRequestDto;
import com.agentcoon.incomecalculator.api.MonthlyNetIncomeDto;
import com.agentcoon.incomecalculator.app.boot.configuration.ExchangeRateProperties;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.Money;
import com.agentcoon.incomecalculator.domain.calculator.IncomeCalculator;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IncomeCalculatorControllerTest {

    private ExchangeRateProperties exchangeRateProperties;
    private IncomeCalculator incomeCalculator;
    private MoneyMapper moneyMapper;

    private IncomeCalculatorController incomeCalculatorController;

    @Before
    public void setUp() {
        exchangeRateProperties = mock(ExchangeRateProperties.class);
        incomeCalculator = mock(IncomeCalculator.class);
        moneyMapper = mock(MoneyMapper.class);

        incomeCalculatorController = new IncomeCalculatorController(exchangeRateProperties, incomeCalculator,
                moneyMapper);
    }

    @Test
    public void calculateIncome() throws NotFoundException {
        String countryCode = "UK";
        BigDecimal dailyRate = BigDecimal.valueOf(1000);
        BigDecimal calculatedMonthlyNetAmount = BigDecimal.valueOf(20000);

        String targetCurrencyCode = "PLN";
        Currency targetCurrency = new Currency(targetCurrencyCode);

        Money calculatedIncome = new Money.Builder().withAmount(calculatedMonthlyNetAmount).build();
        MonthlyNetIncomeDto calculatedIncomeResponse = new MonthlyNetIncomeDto.Builder().withAmount(calculatedMonthlyNetAmount).build();

        IncomeRequestDto requestDto = new IncomeRequestDto(countryCode, dailyRate);

        when(exchangeRateProperties.getTargetCurrency()).thenReturn(targetCurrencyCode);
        when(incomeCalculator.calculate(dailyRate, countryCode, targetCurrency))
                .thenReturn(calculatedIncome);
        when(moneyMapper.from(calculatedIncome)).thenReturn(calculatedIncomeResponse);

        ResponseEntity<MonthlyNetIncomeDto> response = incomeCalculatorController.calculateIncome(requestDto);
        MonthlyNetIncomeDto dto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(calculatedMonthlyNetAmount, dto.getAmount());
    }

    @Test
    public void calculateIncomeWhenCountryNotFound() throws NotFoundException {
        String countryCode = "UK";
        BigDecimal dailyRate = BigDecimal.valueOf(1000);

        String targetCurrencyCode = "PLN";
        Currency targetCurrency = new Currency(targetCurrencyCode);

        IncomeRequestDto requestDto = new IncomeRequestDto(countryCode, dailyRate);

        when(exchangeRateProperties.getTargetCurrency()).thenReturn(targetCurrencyCode);
        doThrow(NotFoundException.class).when(incomeCalculator).calculate(dailyRate, countryCode, targetCurrency);

        ResponseEntity<MonthlyNetIncomeDto> response = incomeCalculatorController.calculateIncome(requestDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void calculateIncomeWhenInputInvalid() throws NotFoundException {
        String countryCode = "UK";
        BigDecimal dailyRate = BigDecimal.valueOf(-0.2);

        IncomeRequestDto requestDto = new IncomeRequestDto(countryCode, dailyRate);

        ResponseEntity<MonthlyNetIncomeDto> response = incomeCalculatorController.calculateIncome(requestDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(exchangeRateProperties, never()).getTargetCurrency();
        verify(incomeCalculator, never()).calculate(any(), any(), any());
    }
}
