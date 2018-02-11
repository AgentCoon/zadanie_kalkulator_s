package com.agentcoon.incomecalculator.acceptancetest

class TestParams {

    /**
     * URL to application under test
     */
    def static APP_PORT = 8080
    def static APP_BASE_URL = "http://localhost"
    def static APP_CONTEXT_PATH = "/"

    /**
     * Connection parameters for a basic simulation of the Fixer currency exchange service
     */
    static MOCK_FIXER_SERVICE_PORT = 8581
    static MOCK_FIXER_CONTEXT_PATH = "/"
}