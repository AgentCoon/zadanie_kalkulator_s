package com.agentcoon.incomecalculator.app.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.agentcoon")
@Configuration
@ConfigurationProperties("exchange")
@EnableConfigurationProperties
public class ExchangeRateProperties {

    private String baseCurrency;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}
