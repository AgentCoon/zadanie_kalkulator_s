package com.agentcoon.incomecalculator.infrastructure.jpa;

import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.CountryRepository;
import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.TaxRate;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryCountryRepository implements CountryRepository {

    private List<Country> countries = new ArrayList<>();

    public InMemoryCountryRepository() {
        populateDummyData();
    }

    @Override
    public List<Country> findAll() {
        return countries;
    }

    @Override
    public Country findOneByCountryCode(String countryCode) throws NotFoundException {
        return countries.stream()
                .filter(e -> e.getCountryCode().equals(countryCode)).findFirst()
                .orElseThrow(() -> new NotFoundException("Country with code " + countryCode + " not found"));
    }

    private void populateDummyData() {
        TaxRate polandTaxRate = new TaxRate.Builder()
                .withRate(BigDecimal.valueOf(0.19))
                .withFixedCost(BigDecimal.valueOf(1200)).build();

        TaxRate ukTaxRate = new TaxRate.Builder()
                .withRate(BigDecimal.valueOf(0.25))
                .withFixedCost(BigDecimal.valueOf(600)).build();

        TaxRate germanyTaxRate = new TaxRate.Builder()
                .withRate(BigDecimal.valueOf(0.2))
                .withFixedCost(BigDecimal.valueOf(800)).build();

        Currency pln = new Currency("PLN");
        Currency eur = new Currency("EUR");
        Currency gbp = new Currency("GBP");

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
