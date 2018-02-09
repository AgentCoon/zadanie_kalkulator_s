package com.agentcoon.incomecalculator.api;

public class CountryDto {

    private String name;
    private String currency;

    public CountryDto(String name, String currency) {
        this.name = name;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public static class Builder {
        private String name;
        private String currency;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CountryDto build() {
            return new CountryDto(name, currency);
        }
    }
}
