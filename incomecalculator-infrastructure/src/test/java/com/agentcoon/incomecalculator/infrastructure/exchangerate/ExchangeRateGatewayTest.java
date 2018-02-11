package com.agentcoon.incomecalculator.infrastructure.exchangerate;

import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProvider;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProviderStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExchangeRateGatewayTest {

    private String provider = "exchangeRateProvider";
    private ExchangeRateProviderStrategy exchangeRateProviderStrategy;

    private ExchangeRateGateway exchangeRateGateway;

    @Before
    public void setUp() {
        exchangeRateProviderStrategy = mock(ExchangeRateProviderStrategy.class);

        exchangeRateGateway = new ExchangeRateGateway(exchangeRateProviderStrategy, provider);
    }

    @Test
    public void getExchangeRate() throws ExchangeRateNotFoundException {
        String pln = "PLN";
        String gbp = "GBP";

        BigDecimal expectedRate = BigDecimal.valueOf(4);
        ExchangeRateProvider exchangeRateProvider = mock(ExchangeRateProvider.class);

        when(exchangeRateProviderStrategy.from(provider)).thenReturn(exchangeRateProvider);
        when(exchangeRateProvider.getExchangeRate(gbp, pln)).thenReturn(expectedRate);

        BigDecimal result = exchangeRateGateway.getExchangeRate(gbp, pln);
        assertEquals(expectedRate, result);
        verify(exchangeRateProviderStrategy, times(1)).from(provider);
        verify(exchangeRateProvider, times(1)).getExchangeRate(gbp, pln);
    }
}
