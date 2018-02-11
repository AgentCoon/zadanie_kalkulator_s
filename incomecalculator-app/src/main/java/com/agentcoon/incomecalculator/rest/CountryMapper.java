package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.CountryDto;
import com.agentcoon.incomecalculator.domain.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    public CountryDto from(Country country) {
        return new CountryDto.Builder()
                .withName(country.getName())
                .withCountryCode(country.getCountryCode())
                .withCurrency(country.getCurrencyCode()).build();
    }
}
