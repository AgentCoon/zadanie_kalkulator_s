package com.agentcoon.incomecalculator.exchangerate.provider;

import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExchangeRateProviderStrategy {

    private final Map<String, ExchangeRateProvider> exchangeRateProviders;

    @Autowired
    public ExchangeRateProviderStrategy(Map<String, ExchangeRateProvider> exchangeRateProviders) {
        this.exchangeRateProviders = exchangeRateProviders;
    }

    public ExchangeRateProvider from(String provider) {

        ExchangeRateProvider exchangeRateProvider = exchangeRateProviders.get(provider);

        if (exchangeRateProvider == null) {
            throw new ExchangeRateClientException("Requested exchange rate provider " + provider + " is not supported.");
        }

        return exchangeRateProvider;
    }
}