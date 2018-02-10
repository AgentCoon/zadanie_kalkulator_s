package com.agentcoon.incomecalculator.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class IncomeRequestDto {

    private String countryCode;
    private BigDecimal amount;

    @JsonCreator
    public IncomeRequestDto(@JsonProperty("countryCode") String countryCode,
                            @JsonProperty("amount") BigDecimal amount) {
        this.countryCode = countryCode;
        this.amount = amount;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
