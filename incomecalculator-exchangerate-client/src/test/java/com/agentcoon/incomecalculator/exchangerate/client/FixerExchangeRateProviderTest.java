package com.agentcoon.incomecalculator.exchangerate.client;

import com.agentcoon.incomecalculator.exchangerate.client.api.ExchangeRateApiDto;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.exchangerate.provider.FixerExchangeRateProvider;
import com.agentcoon.incomecalculator.exchangerate.provider.fixer.FixerGateway;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FixerExchangeRateProviderTest {

    private FixerGateway fixerGateway;

    private FixerExchangeRateProvider fixerExchangeRateProvider;

    private Map<String, BigDecimal> rates = new HashMap<>();
    private final String pln = "PLN";
    private final String gbp = "GBP";
    private final String eur = "EUR";

    @Before
    public void setUp() {
        fixerGateway = mock(FixerGateway.class);

        rates.put(gbp, BigDecimal.ONE);
        rates.put(pln, BigDecimal.valueOf(5));
        rates.put(eur, BigDecimal.valueOf(4.53));

        fixerExchangeRateProvider = new FixerExchangeRateProvider(fixerGateway);
    }

    @Test
    public void getExchangeRate() throws ExchangeRateNotFoundException {
        ExchangeRateApiDto exchangeRateApiDto = new ExchangeRateApiDto.Builder()
                .withRates(rates)
                .withBaseCurrency(gbp).build();

        when(fixerGateway.fetchExchangeRates(gbp)).thenReturn(exchangeRateApiDto);

        BigDecimal result = fixerExchangeRateProvider.getExchangeRate(gbp, pln);
        assertEquals(result, BigDecimal.valueOf(5));
    }

    @Test
            (expected=ExchangeRateNotFoundException.class)
    public void getExchangeRateWhenRateNotFound() throws ExchangeRateNotFoundException {
        ExchangeRateApiDto exchangeRateApiDto = new ExchangeRateApiDto.Builder()
                .withRates(rates)
                .withBaseCurrency(gbp).build();

        when(fixerGateway.fetchExchangeRates(gbp)).thenReturn(exchangeRateApiDto);

        fixerExchangeRateProvider.getExchangeRate(gbp, "NOK");
    }
}
