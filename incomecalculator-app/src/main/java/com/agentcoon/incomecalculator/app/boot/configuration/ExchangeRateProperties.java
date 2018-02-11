package com.agentcoon.incomecalculator.app.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@ComponentScan("com.agentcoon")
@Configuration
@ConfigurationProperties("exchangeRateConfiguration")
@EnableConfigurationProperties
public class ExchangeRateProperties {

    @NotNull
    private String targetCurrency;

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
