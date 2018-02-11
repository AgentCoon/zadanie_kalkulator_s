# Monthly Net Income Calculator Application

A Spring Boot application calculating monthly net income and converting it to a target currency.

Integrated with Fixer currency conversion API.


## Requirements

### Java
From [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

JDK 8 is required to build and run the application.

### Maven
[Apache Maven](http://maven.apache.org/download.cgi)


## Quick start

Application runs as a Spring Boot Application.

Run configuration:

module: incomecalculator-app  
main class: com.agentcoon.incomecalculator.app.boot.IncomeCalculatorApplication
parameters: --spring.config.location=${workspace_loc:/incomecalculator-configuration}/src/main/resources/local/incomecalculator.yml


## Build

Using Maven from top level of project
```
mvn clean package verify
```