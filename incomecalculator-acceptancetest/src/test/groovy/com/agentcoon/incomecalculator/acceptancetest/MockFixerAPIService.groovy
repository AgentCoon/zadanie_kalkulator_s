package com.agentcoon.incomecalculator.acceptancetest

import com.github.tomakehurst.wiremock.WireMockServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo

class MockFixerAPIService {

    private Logger logger = LoggerFactory.getLogger(getClass())

    private WireMockServer wireMockServer

    public static final String EXCHANGE_RATES_RESPONSE = "{\n" +
            "  \"date\": \"2018-02-09\",\n" +
            "  \"base\": \"GBP\",\n" +
            "  \"rates\": {\n" +
            "    \"EUR\": 3.673028,\n" +
            "    \"USD\": 6.489666,\n" +
            "    \"GBP\": 1.0 }}"

    void beforeStories() {
        wireMockServer = new WireMockServer(TestParams.MOCK_FIXER_SERVICE_PORT)
        wireMockServer.start()
    }

    void getExchangeRates(String baseCurrency) {
        logger.info("Fetching exchange rates with base {}", baseCurrency)

        wireMockServer.stubFor(get(urlPathEqualTo(TestParams.MOCK_FIXER_CONTEXT_PATH + "latest"))
                .withQueryParam("base", equalTo(baseCurrency))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(EXCHANGE_RATES_RESPONSE)))
    }

    void afterStories() {
        wireMockServer.stop()
    }
}
