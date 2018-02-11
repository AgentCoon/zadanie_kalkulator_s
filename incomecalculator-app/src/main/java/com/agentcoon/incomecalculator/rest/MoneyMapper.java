package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.MonthlyNetIncomeDto;
import com.agentcoon.incomecalculator.domain.Money;
import org.springframework.stereotype.Component;

@Component
public class MoneyMapper {

    public MonthlyNetIncomeDto from(Money money) {
        return new MonthlyNetIncomeDto.Builder()
                .withAmount(money.getAmount())
                .withCurrency(money.getCurrencyCode()).build();
    }
}
