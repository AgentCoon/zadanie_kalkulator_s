package com.agentcoon.incomecalculator.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class IncomeCalculatorController {

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() {

        return "Hello";
    }
}
