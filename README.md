<h1>Robotic Hoover REST</h1>

Spring Boot service that navigates an imaginary robotic hoover

## Overview

This repo was a sample assignment. You can find the annotated assignment 
including comments in [docs/ASSIGNMENT.md](doc/ASSIGNMENT.md).

## Modules

- api: Separate module with models and constants useful API clients. 
- app: Backend REST services application based on Spring Boot.
- domain: Core implementation.

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

Build the project:

	./gradlew clean build

Launch backend services:

	./gradlew app:bootRun

Browse http://localhost:8080/swagger-ui/index.html to explore and try the API using a Swagger UI.