package com.agentcoon.incomecalculator.exchangerate.provider.fixer;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateApiDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerExchangeRateClient {

    @GET("latest")
    Call<ExchangeRateApiDto> getExchangeRates(@Query("base") String baseCurrency);
}