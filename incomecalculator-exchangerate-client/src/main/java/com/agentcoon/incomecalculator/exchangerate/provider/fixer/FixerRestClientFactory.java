package com.agentcoon.incomecalculator.exchangerate.provider.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Component
public class FixerRestClientFactory {

    private final ObjectMapper objectMapper;
    private final FixerConfiguration config;

    @Autowired
    public FixerRestClientFactory(ObjectMapper objectMapper, FixerConfiguration config) {
        this.objectMapper = objectMapper;
        this.config = config;
    }

    public FixerExchangeRateClient create() {
        return getRetrofitInstance(config).create(FixerExchangeRateClient.class);
    }

    private Retrofit getRetrofitInstance(FixerConfiguration config) {
        return new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(config.getUrl())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
