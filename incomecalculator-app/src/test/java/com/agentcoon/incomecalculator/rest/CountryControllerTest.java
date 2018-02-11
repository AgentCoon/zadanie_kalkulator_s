package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.CountryDto;
import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryControllerTest {

    private CountryRepository countryRepository;
    private CountryMapper mapper;

    private CountryController countryController;

    @Before
    public void setUp() {
        countryRepository = mock(CountryRepository.class);
        mapper = mock(CountryMapper.class);

        countryController = new CountryController(countryRepository, mapper);
    }

    @Test
    public void getCountries() {
        String countryCode = "UK";

        Country country = new Country.Builder().withCountryCode(countryCode).build();
        CountryDto dto = new CountryDto.Builder().withCountryCode(countryCode).build();

        when(countryRepository.findAll()).thenReturn(Collections.singletonList(country));
        when(mapper.from(country)).thenReturn(dto);

        ResponseEntity<List<CountryDto>> result = countryController.getCountries();
        List<CountryDto> returnedCountries = result.getBody();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, returnedCountries.size());
        assertEquals(countryCode, returnedCountries.get(0).getCountryCode());
    }
}
