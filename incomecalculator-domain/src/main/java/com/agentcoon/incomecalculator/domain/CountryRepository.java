package com.agentcoon.incomecalculator.domain;

import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CountryRepository {

    List<Country> findAll();

    Country findOneByCountryCode(String countryCode) throws NotFoundException;
}
