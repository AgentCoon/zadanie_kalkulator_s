package com.agentcoon.incomecalculator.domain.calculator;

import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.TaxRate;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(DataProviderRunner.class)
public class MonthlyNetIncomeCalculatorTest {

    private final int daysInMonth = 22;

    private MonthlyNetIncomeCalculator monthlyNetIncomeCalculator;

    @Before
    public void setUp() {
        monthlyNetIncomeCalculator = new MonthlyNetIncomeCalculator(daysInMonth);
    }

    @Test
    @UseDataProvider("monthlyNetIncomeCalculatorDataProvider")
    public void test(BigDecimal dailyRate, Country country, BigDecimal expectedResult) {

        BigDecimal result = monthlyNetIncomeCalculator.calculateNetMonthlyIncome(dailyRate, country);
        assertThat(expectedResult, Matchers.comparesEqualTo(result));
    }

    @DataProvider
    public static Object[][] monthlyNetIncomeCalculatorDataProvider() {
        String pln = "PLN";
        String gbp = "GBP";

        BigDecimal plRate = BigDecimal.valueOf(0.19);
        BigDecimal plFixedCost = BigDecimal.valueOf(1000);

        TaxRate plTaxRate  = new TaxRate.Builder()
                .withRate(plRate)
                .withFixedCost(plFixedCost).build();

        Country poland = new Country.Builder()
                .withCountryCode("PL")
                .withCurrency(new Currency(pln))
                .withTaxRate(plTaxRate).build();

        BigDecimal ukRate = BigDecimal.valueOf(0.25);
        BigDecimal ukFixedCost = BigDecimal.valueOf(500);

        TaxRate ukTaxRate  = new TaxRate.Builder()
                .withRate(ukRate)
                .withFixedCost(ukFixedCost).build();

        Country unitedKingdom = new Country.Builder()
                .withCountryCode("UK")
                .withCurrency(new Currency(gbp))
                .withTaxRate(ukTaxRate).build();

        return new Object[][] {
                { BigDecimal.valueOf(0), unitedKingdom, BigDecimal.valueOf(0) },
                { BigDecimal.valueOf(10), unitedKingdom, BigDecimal.valueOf(220.0) },
                { BigDecimal.valueOf(500), unitedKingdom, BigDecimal.valueOf(8375.0) },
                { BigDecimal.valueOf(1000), unitedKingdom, BigDecimal.valueOf(16625.0) },
                { BigDecimal.valueOf(0), poland, BigDecimal.valueOf(0) },
                { BigDecimal.valueOf(10), poland, BigDecimal.valueOf(220) },
                { BigDecimal.valueOf(500), poland, BigDecimal.valueOf(9100.0) },
                { BigDecimal.valueOf(1000), poland, BigDecimal.valueOf(18010.0) },
                { BigDecimal.valueOf(1125.25), poland, BigDecimal.valueOf(20241.96) },
                { BigDecimal.valueOf(102.32), poland, BigDecimal.valueOf(2013.34) }
        };
    }
}
