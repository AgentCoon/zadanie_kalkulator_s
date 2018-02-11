package com.agentcoon.incomecalculator.infrastructure.exchangerate;

import com.agentcoon.incomecalculator.domain.Currency;
import com.agentcoon.incomecalculator.domain.exception.NotFoundException;
import com.agentcoon.incomecalculator.exchangerate.exception.ExchangeRateNotFoundException;
import com.agentcoon.incomecalculator.infrastructure.currencyconverter.CurrencyConverterImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyConverterImplTest {

    private ExchangeRateGateway exchangeRateGateway;

    private CurrencyConverterImpl currencyConverter;

    private final String plnCode = "PLN";
    private final String gbpCode = "GBP";

    private final Currency pln = new Currency(plnCode);
    private final Currency gbp = new Currency(gbpCode);

    @Before
    public void setUp() {
        exchangeRateGateway = mock(ExchangeRateGateway.class);

        currencyConverter = new CurrencyConverterImpl(exchangeRateGateway);
    }

    @Test
    public void convertTest() throws NotFoundException, ExchangeRateNotFoundException {

        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal exchangeRate = BigDecimal.valueOf(0.21);

        BigDecimal expectedAmount = BigDecimal.valueOf(21);

        when(exchangeRateGateway.getExchangeRate(plnCode, gbpCode)).thenReturn(exchangeRate);

        BigDecimal result = currencyConverter.convert(pln, gbp, amount);
        assertThat(expectedAmount, Matchers.comparesEqualTo(result));
    }

    @Test
            (expected=NotFoundException.class)
    public void convertWhenRateNotFound() throws NotFoundException, ExchangeRateNotFoundException {

        BigDecimal amount = BigDecimal.valueOf(100);

        doThrow(ExchangeRateNotFoundException.class)
                .when(exchangeRateGateway).getExchangeRate(plnCode, gbpCode);

        currencyConverter.convert(pln, gbp, amount);
    }
}
