package com.agentcoon.incomecalculator.domain;

public class Country {

    private final String name;
    private final String countryCode;
    private final TaxRate taxRate;
    private final Currency currency;

    private Country(String name, String countryCode, TaxRate taxRate, Currency currency) {
        this.name = name;
        this.countryCode = countryCode;
        this.taxRate = taxRate;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public static class Builder {
        private String name;
        private String countryCode;
        private TaxRate taxRate;
        private Currency currency;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder withTaxRate(TaxRate taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Country build() {
            return new Country(name, countryCode, taxRate, currency);
        }
    }
}
