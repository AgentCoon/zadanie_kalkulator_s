package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.IncomeRequestDto;
import com.agentcoon.incomecalculator.api.MonthlyNetIncomeDto;
import com.agentcoon.incomecalculator.app.boot.configuration.ExchangeRateProperties;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.Money;
import com.agentcoon.incomecalculator.domain.calculator.IncomeCalculator;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import com.agentcoon.incomecalculator.rest.validation.IncomeRequestValidator;
import com.agentcoon.incomecalculator.rest.validation.IncomeValidationException;
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
    private final IncomeCalculator incomeCalculator;
    private final MoneyMapper moneyMapper;

    @Autowired
    public IncomeCalculatorController(ExchangeRateProperties exchangeRateProperties,
                                      IncomeCalculator incomeCalculator,
                                      MoneyMapper moneyMapper) {
        this.exchangeRateProperties = exchangeRateProperties;
        this.incomeCalculator = incomeCalculator;
        this.moneyMapper = moneyMapper;
    }

    @RequestMapping(path = "/calculate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<MonthlyNetIncomeDto> calculateIncome(@RequestBody IncomeRequestDto dto) {

        try {
            IncomeRequestValidator.validate(dto);

            Currency targetCurrency = new Currency(exchangeRateProperties.getTargetCurrency());
            Money calculatedNetIncome = incomeCalculator.calculate(dto.getAmount(), dto.getCountryCode(), targetCurrency);

            return ResponseEntity.ok().body(moneyMapper.from(calculatedNetIncome));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (IncomeValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
