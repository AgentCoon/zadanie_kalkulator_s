package com.agentcoon.incomecalculator.exchangerate.provider.fixer;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.stereotype.Component;

@Component
public class FixerRestConfiguration {

    private final String url = "https://api.fixer.io/";

    @JsonCreator
    public FixerRestConfiguration() {
        //this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
