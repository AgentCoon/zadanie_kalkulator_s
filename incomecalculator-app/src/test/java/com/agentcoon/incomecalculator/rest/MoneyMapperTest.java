package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.MonthlyNetIncomeDto;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MoneyMapperTest {

    private MoneyMapper mapper = new MoneyMapper();

    @Test
    public void from() {
        BigDecimal amount = BigDecimal.valueOf(10);
        Currency currency = new Currency("GBP");

        Money money = new Money.Builder()
                .withCurrency(currency)
                .withAmount(amount).build();

        MonthlyNetIncomeDto dto = mapper.from(money);
        assertEquals(amount, dto.getAmount());
        assertEquals(currency.getCurrencyCode(), dto.getCurrency());
    }
}
