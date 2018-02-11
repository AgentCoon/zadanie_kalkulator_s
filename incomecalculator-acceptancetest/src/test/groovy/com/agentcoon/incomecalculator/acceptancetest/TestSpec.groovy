package com.agentcoon.incomecalculator.acceptancetest

import com.agentcoon.incomecalculator.api.CountryDto
import com.agentcoon.incomecalculator.api.MonthlyNetIncomeDto
import spock.lang.Shared
import spock.lang.Specification

class TestSpec extends Specification {

    @Shared
    IncomeCalculatorDriver incomecalculator = new IncomeCalculatorDriver()

    @Shared
    MockFixerAPIService fixerAPIService = new MockFixerAPIService()

    def setupSpec() {
        fixerAPIService.beforeStories()
    }

    def cleanupSpec() {
        fixerAPIService.afterStories()
    }

    def "List all available countries"() {
        when: "A request is made to get all available countries"

        List<CountryDto> dtos = incomecalculator.getAllAvailableCountries()

        then: "All available countries are returned"
        incomecalculator.allCountriesAreReturned(dtos)
    }

    def "Calculate monthly PL income to PLN"() {
        String countryCode = "PL"
        BigDecimal amount = 235
        BigDecimal expectedValue = 4415.70

        when: "A request is made to calculate income in PLN"
        MonthlyNetIncomeDto dto = incomecalculator.calculateNetIncome(countryCode, amount)

        then: "Monthly net income is calculated"
        incomecalculator.verifyMonthlyNetIncome(dto, expectedValue)
    }

    def "Calculate monthly UK income to PLN"() {
        String countryCode = "UK"
        BigDecimal amount = 252.5
        BigDecimal expectedValue = 20392.50

        given:
        fixerAPIService.register()

        when: "A request is made to calculate income in PLN"
        MonthlyNetIncomeDto dto = incomecalculator.calculateNetIncome(countryCode, amount)

        then: "Monthly net income is calculated"
        incomecalculator.verifyMonthlyNetIncome(dto, expectedValue)
    }
}
