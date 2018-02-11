package com.agentcoon.incomecalculator.acceptancetest

import com.github.tomakehurst.wiremock.WireMockServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.github.tomakehurst.wiremock.client.WireMock.*

class MockFixerAPIService {

    private Logger logger = LoggerFactory.getLogger(getClass())

    private WireMockServer wireMockServer

    public static final String EXCHANGE_RATES_RESPONSE = "{\n" +
            "  \"date\": \"2018-02-09\",\n" +
            "  \"base\": \"GBP\",\n" +
            "  \"rates\": {\n" +
            "    \"EUR\": 1.673028,\n" +
            "    \"USD\": 1.489666,\n" +
            "    \"PLN\": 4.724587 }}"

    void beforeStories() {
        wireMockServer = new WireMockServer(TestParams.MOCK_FIXER_SERVICE_PORT)
        wireMockServer.start()
    }

    void register() {
        logger.info("Registering Fixer Mock for GBP as base currency")

        wireMockServer.stubFor(get(urlPathEqualTo(TestParams.MOCK_FIXER_CONTEXT_PATH + "latest"))
                .withQueryParam("base", equalTo("GBP"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(EXCHANGE_RATES_RESPONSE)))
    }

    void afterStories() {
        wireMockServer.stop()
    }
}
