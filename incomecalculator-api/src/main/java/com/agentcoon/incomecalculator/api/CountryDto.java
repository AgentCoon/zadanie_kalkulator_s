package com.agentcoon.incomecalculator.api;

public class CountryDto {

    private String name;
    private String countryCode;
    private String currency;

    public CountryDto() {}

    private CountryDto(String name, String countryCode, String currency) {
        this.name = name;
        this.countryCode = countryCode;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public static class Builder {
        private String name;
        private String countryCode;
        private String currency;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CountryDto build() {
            return new CountryDto(name, countryCode, currency);
        }
    }
}
