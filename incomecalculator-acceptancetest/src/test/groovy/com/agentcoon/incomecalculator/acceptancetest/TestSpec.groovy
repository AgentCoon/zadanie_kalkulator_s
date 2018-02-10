package com.agentcoon.incomecalculator.acceptancetest

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
        when: "Hello"

        then: "A request is made to get all available countries"
        incomecalculator.getAllAvailableCountries()
    }
}
