package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.CountryDto;
import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.Currency;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryMapperTest {

    private CountryMapper mapper = new CountryMapper();

    @Test
    public void from() {
        String countryCode = "UK";
        String name = "United Kingdom";
        Currency currency = new Currency("GBP");

        Country country = new Country.Builder()
                .withCountryCode(countryCode)
                .withName(name)
                .withCurrency(currency)
                .build();

        CountryDto dto = mapper.from(country);
        assertEquals(countryCode, dto.getCountryCode());
        assertEquals(name, dto.getName());
        assertEquals(currency.getCurrencyCode(), dto.getCurrency());
    }
}
