# Robotic Hoover REST [![Build and Test on Push](https://github.com/manosbatsis/robotic-hoover-rest/actions/workflows/build.yml/badge.svg)](https://github.com/manosbatsis/robotic-hoover-rest/actions/workflows/build.yml)

Spring Boot service that navigates an imaginary robotic hoover

## Overview

This repo was a sample assignment. You can find the annotated assignment 
including comments/assumptions (see $${\color{green}Tip}$$ sections) in [docs/ASSIGNMENT.md](doc/ASSIGNMENT.md).

### API Versions

Based on our assignment comments (see above) this repo provides two API versions:

- v1.0 as requested in the assignment
- v2.0 according to our comments.

## Modules

- api: Open API definitions, models and constants useful to API clients. 
  See packages for API [v1](api/src/main/kotlin/com/github/manosbatsis/robotichooverrest/api/instruction/v1) 
  and [v2](api/src/main/kotlin/com/github/manosbatsis/robotichooverrest/api/instruction/v2).
- app: Backend REST services application based on Spring Boot. See packages for API 
  [v1](app/src/main/kotlin/com/github/manosbatsis/robotichooverrest/app/instruction/v1) 
  and [v2](app/src/main/kotlin/com/github/manosbatsis/robotichooverrest/app/instruction/v2).
- client: API client based on [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign), 
  see packages [here](client/src/main/kotlin/com/github/manosbatsis/robotichooverrest/client/instruction).
- domain: Framework-independent core implementation, see package [here](domain/src/main/kotlin/com/github/manosbatsis/robotichooverrest/domain/instruction).

This allows some flexibility and proper decoupling, like creating the client module and even use that from within 
`app` module tests without circular dependencies. Breaking v1 and v2 bits to separate modules was considered 
unnecessary complexity for this repo.

## RESTful Errors and Validation

The app uses [Bean Validation](https://beanvalidation.org/)
and [wimdeblauwe's error handling starter](https://wimdeblauwe.github.io/error-handling-spring-boot-starter/current/) 
to provide input validation and RESTful exception handling respectively. 

For quick examples see [doc/ERRORS_AND_VALIDATION.md](doc/ERRORS_AND_VALIDATION.md).

## Build HowTo

**Build the project** and run tests:

	./gradlew clean build

We only provide a couple of quick, basic tests per API version:

- One for the "happy path" per assignment description and/or comments and assumptions.
- One to verify our validation and exception handling libraries are configured properly; 
  we assume their functionality is properly tested upstream. Thorough validation etc. testing 
  is considered out-of-scope effort for this repo.

**Launch backend** services:

	./gradlew app:bootRun

Browse http://localhost:8080/swagger-ui/index.html to explore and try the API using a Swagger UI.

### Formatting

This project uses [Spotless](https://github.com/diffplug/spotless?tab=readme-ov-file#-spotless-keep-your-code-spotless) 
and [ktfmt](https://facebook.github.io/ktfmt/) for code formatting. If you edit a file and the build complains, simply 
run spotless to format your code:

    ./gradlew :spotlessApply