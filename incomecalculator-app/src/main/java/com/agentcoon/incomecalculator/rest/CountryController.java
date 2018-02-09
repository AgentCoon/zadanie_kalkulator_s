package com.agentcoon.incomecalculator.rest;

import com.agentcoon.incomecalculator.api.CountryDto;
import com.agentcoon.incomecalculator.domain.Country;
import com.agentcoon.incomecalculator.domain.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.core.MediaType;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
public class CountryController {

    private final CountryRepository countryRepository;
    private final CountryMapper mapper;

    @Autowired
    public CountryController(CountryRepository countryRepository, CountryMapper mapper) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    @RequestMapping(path = "/countries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<List<CountryDto>> getCountries() {

        List<Country> countries = countryRepository.findAll();

        return ResponseEntity.ok().body(countries.stream()
                .map(mapper::from)
                .collect(toList()));
    }
}
