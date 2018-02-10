package com.agentcoon.incomecalculator.acceptancetest

import com.agentcoon.incomecalculator.api.CountryDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.http.ContentType
import com.jayway.restassured.specification.RequestSpecification
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.jayway.restassured.RestAssured.given
import static org.junit.Assert.assertEquals

class IncomeCalculatorDriver {

    Logger logger = LoggerFactory.getLogger(getClass())

    RequestSpecification appSpec = new RequestSpecBuilder()
            .setBaseUri(TestParams.APP_BASE_URL)
            .setPort(TestParams.APP_PORT)
            .setBasePath(TestParams.APP_CONTEXT_PATH)
            .setContentType(ContentType.JSON).build()

    ObjectMapper mapper = new ObjectMapper()


    String getAllAvailableCountries() {
        logger.info("Get all available countries")

        String json = given().spec(appSpec).expect().statusCode(200)
                .when().get("/countries").asString()

        List<CountryDto> countryDtos = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, CountryDto.class))

        assertEquals(3, countryDtos.size())
    }
}
