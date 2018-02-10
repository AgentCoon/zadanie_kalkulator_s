package com.agentcoon.incomecalculator.exchangerate.provider.fixer;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateApiDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Component
public class FixerGateway {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final FixerExchangeRateClient fixerExchangeRateClient;

    @Autowired
    public FixerGateway(FixerRestClientFactory fixerRestClientFactory) {
        fixerExchangeRateClient = fixerRestClientFactory.create();
    }

    public ExchangeRateApiDto fetchExchangeRates(String baseCurrency) {
        return send(fixerExchangeRateClient.getExchangeRates(baseCurrency), "failed to fetch exchange rates");
    }

    private <T> T send(Call<T> call, String errorMsg) {

        try {
            Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                logger.error("Error response {} from Fixer API, {}", response, errorMsg);
                throw new ExchangeRateClientException(errorMsg);
            }

            return response.body();

        } catch (IOException e) {
            throw new ExchangeRateClientException("IOException, " + errorMsg, e);
        }
    }
}
