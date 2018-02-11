package com.agentcoon.incomecalculator.domain.calculator;

import com.agentcoon.incomecalculator.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MonthlyNetIncomeCalculator {

    private final int daysInMonth;

    @Autowired
    public MonthlyNetIncomeCalculator(@Value("${daysInMonth}") int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public BigDecimal calculateNetMonthlyIncome(BigDecimal dailyRate, Country country) {
        BigDecimal monthlyIncome = dailyRate.multiply(BigDecimal.valueOf(daysInMonth));
        BigDecimal monthlyTaxableIncome = BigDecimal.valueOf(Math.max(0.0f, monthlyIncome.subtract(country.getFixedCostValue()).floatValue()));
        BigDecimal tax = monthlyTaxableIncome.multiply(country.getTaxRateValue());

        return monthlyIncome.subtract(tax).setScale(2, RoundingMode.HALF_UP);
    }
}
