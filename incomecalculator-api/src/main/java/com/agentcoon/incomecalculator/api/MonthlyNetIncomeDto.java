package com.agentcoon.incomecalculator.api;

import java.math.BigDecimal;

public class MonthlyNetIncomeDto {

    private BigDecimal amount;
    private String currency;

    public MonthlyNetIncomeDto() {}

    private MonthlyNetIncomeDto(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public static class Builder {
        private BigDecimal amount;
        private String currency;

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public MonthlyNetIncomeDto build() {
            return new MonthlyNetIncomeDto(amount, currency);
        }
    }
}
