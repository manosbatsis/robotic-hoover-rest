# Robotic Hoover REST [![Build and Test on Push](https://github.com/manosbatsis/robotic-hoover-rest/actions/workflows/build.yml/badge.svg)](https://github.com/manosbatsis/robotic-hoover-rest/actions/workflows/build.yml)

Spring Boot service that navigates an imaginary robotic hoover

## Overview

This repo was a sample assignment. You can find the annotated assignment 
including comments/assumptions in [docs/ASSIGNMENT.md](doc/ASSIGNMENT.md).

## Modules

- api: Separate module with models and constants useful API clients. 
  See packages for API [v1](api/src/main/kotlin/com/github/manosbatsis/robotichooverrest/api/instruction/v1) 
  and [v2](api/src/main/kotlin/com/github/manosbatsis/robotichooverrest/api/instruction/v2).
- app: Backend REST services application based on Spring Boot. See packages for API 
  [v1](app/src/main/kotlin/com/github/manosbatsis/robotichooverrest/app/instruction/v1) 
  and [v2](app/src/main/kotlin/com/github/manosbatsis/robotichooverrest/app/instruction/v2).
- domain: Core implementation, see `HooverState` [here](domain/src/main/kotlin/com/github/manosbatsis/robotichooverrest/domain/instruction).

## API Versions

Based on our assignment comments this repo provides two API versions:

- v1.0 as requested in the assignment
- v2.0 according to our comments, see  [docs/ASSIGNMENT.md](doc/ASSIGNMENT.md).

## RESTful Errors and Validation

The app uses [Bean Validation](https://beanvalidation.org/)
and an [wimdeblauwe's error handling starter](https://wimdeblauwe.github.io/error-handling-spring-boot-starter/current/) 
to provide input validation and RESTful exception handling respectively, both in a horizontal manner. 

For quick examples see [doc/ERRORS_AND_VALIDATION.md](doc/ERRORS_AND_VALIDATION.md).

## Build HowTo

Build the project and run tests:

	./gradlew clean build

We only provide a couple of basic tests per API version:

- One for the "happy path" per assignment description and/or comments and assumptions.
- One to verify our validation and exception handling libraries are configured properly; 
  we assume their functionality is properly tested upstream. 

Launch backend services:

	./gradlew app:bootRun

Browse http://localhost:8080/swagger-ui/index.html to explore and try the API using a Swagger UI.