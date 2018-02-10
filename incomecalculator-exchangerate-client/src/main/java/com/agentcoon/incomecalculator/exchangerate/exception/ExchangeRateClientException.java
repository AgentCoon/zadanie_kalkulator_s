package com.agentcoon.incomecalculator.exchangerate.exception;

public class ExchangeRateClientException extends RuntimeException {

    private static final long serialVersionUID = 3388659785314022567L;

    public ExchangeRateClientException(String message) {
        super(message);
    }

    public ExchangeRateClientException(String message, Throwable cause) {
        super(message, cause);
    }
}