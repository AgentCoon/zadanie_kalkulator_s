package com.agentcoon.incomecalculator.infrastructure.exchangerate;

import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProvider;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProviderStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExchangeRateGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String provider;

    private final ExchangeRateProviderStrategy exchangeRateProviderStrategy;

    @Autowired
    public ExchangeRateGateway(ExchangeRateProviderStrategy exchangeRateProviderStrategy,
                               @Value("${exchangeRateConfiguration.provider}") String provider) {
        this.exchangeRateProviderStrategy = exchangeRateProviderStrategy;
        this.provider = provider;
    }

    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) throws ExchangeRateNotFoundException {
        logger.info("Fetching exchange rate from {}", provider);

        ExchangeRateProvider exchangeRateProvider = exchangeRateProviderStrategy.from(provider);

        return exchangeRateProvider.getExchangeRate(sourceCurrency, targetCurrency);
    }
}
