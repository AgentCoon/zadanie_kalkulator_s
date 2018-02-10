package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.IncomeRequestDto;
import com.agentcoon.incomecalculator.api.MonthlyNetIncomeDto;
import com.agentcoon.incomecalculator.app.boot.configuration.ExchangeRateProperties;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.Money;
import com.agentcoon.incomecalculator.domain.calculator.MonthlyNetIncomeCalculator;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
public class IncomeCalculatorController {

    private final ExchangeRateProperties exchangeRateProperties;
    private final MonthlyNetIncomeCalculator monthlyNetIncomeCalculator;
    private final MoneyMapper moneyMapper;

    @Autowired
    public IncomeCalculatorController(ExchangeRateProperties exchangeRateProperties,
                                      MonthlyNetIncomeCalculator monthlyNetIncomeCalculator,
                                      MoneyMapper moneyMapper) {
        this.exchangeRateProperties = exchangeRateProperties;
        this.monthlyNetIncomeCalculator = monthlyNetIncomeCalculator;
        this.moneyMapper = moneyMapper;
    }

    @RequestMapping(path = "/calculate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<MonthlyNetIncomeDto> calculateIncome(@RequestBody IncomeRequestDto dto) {

        try {
            Currency targetCurrency = new Currency(exchangeRateProperties.getBaseCurrency());
            Money calculatedNetIncome = monthlyNetIncomeCalculator.calculate(dto.getAmount(), dto.getCountryCode(), targetCurrency);

            return ResponseEntity.ok().body(moneyMapper.from(calculatedNetIncome));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
