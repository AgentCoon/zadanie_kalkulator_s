package com.agentcoon.incomecalculator.infrastructure.jpa;

import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.CountryRepository;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.TaxRate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryInMemoryRepository implements CountryRepository {

    private List<Country> countries = new ArrayList<>();

    @Override
    public List<Country> findAll() {
        populateDummyData();

        return countries;
    }

    private void populateDummyData() {
        TaxRate polandTaxRate = new TaxRate.Builder()
                .withRate(0.19f)
                .withFixedCost(1200f).build();

        TaxRate ukTaxRate = new TaxRate.Builder()
                .withRate(0.25f)
                .withFixedCost(600f).build();

        TaxRate germanyTaxRate = new TaxRate.Builder()
                .withRate(0.2f)
                .withFixedCost(800f).build();

        Currency pln = new Currency.Builder().withCurrencyCode("PLN").build();
        Currency eur = new Currency.Builder().withCurrencyCode("EUR").build();
        Currency gbp = new Currency.Builder().withCurrencyCode("GBP").build();

        Country poland = new Country.Builder()
                .withName("Poland")
                .withCountryCode("PL")
                .withTaxRate(polandTaxRate)
                .withCurrency(pln)
                .build();

        Country unitedKingdom = new Country.Builder()
                .withName("United Kingdom")
                .withCountryCode("UK")
                .withTaxRate(ukTaxRate)
                .withCurrency(gbp).build();

        Country germany = new Country.Builder()
                .withName("Germany")
                .withCountryCode("DE")
                .withTaxRate(germanyTaxRate)
                .withCurrency(eur).build();

        countries.add(poland);
        countries.add(unitedKingdom);
        countries.add(germany);
    }
}
