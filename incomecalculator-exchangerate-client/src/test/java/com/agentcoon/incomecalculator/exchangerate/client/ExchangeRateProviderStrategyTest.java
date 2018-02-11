package com.agentcoon.incomecalculator.exchangerate.client;

import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateClientException;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProvider;
import com.agentcoon.incomecalculator.exchangerate.provider.ExchangeRateProviderStrategy;
import com.agentcoon.incomecalculator.exchangerate.provider.FixerExchangeRateProvider;
import com.agentcoon.incomecalculator.exchangerate.provider.InMemoryExchangeRateProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ExchangeRateProviderStrategyTest {

    private Map<String, ExchangeRateProvider> exchangeRateProviders = new HashMap<>();

    private ExchangeRateProviderStrategy exchangeRateProviderStrategy;

    @Before
    public void setUp() {
        FixerExchangeRateProvider fixerProvider = mock(FixerExchangeRateProvider.class);
        InMemoryExchangeRateProvider inMemoryExchangeRateProvider = mock(InMemoryExchangeRateProvider.class);

        exchangeRateProviders.put("fixerProvider", fixerProvider);
        exchangeRateProviders.put("inMemoryProvider", inMemoryExchangeRateProvider);

        exchangeRateProviderStrategy = new ExchangeRateProviderStrategy(exchangeRateProviders);
    }

    @Test
    public void fromTest() {

        ExchangeRateProvider provider = exchangeRateProviderStrategy.from("fixerProvider");
        assertThat(provider, instanceOf(FixerExchangeRateProvider.class));

        provider = exchangeRateProviderStrategy.from("inMemoryProvider");
        assertThat(provider, instanceOf(InMemoryExchangeRateProvider.class));
    }

    @Test
            (expected=ExchangeRateClientException.class)
    public void fromWhenNotFoundTest() {
        exchangeRateProviderStrategy.from("exchangeRateProviderThatDoesNotExist");
    }
}
