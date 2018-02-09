package com.agentcoon.incomecalculator.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CountryRepository {

    List<Country> findAll();
}
