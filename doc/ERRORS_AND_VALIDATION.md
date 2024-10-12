# RESTful Errors and Validation

The project uses [Bean Validation](https://beanvalidation.org/) 
and an [error handling starter](https://wimdeblauwe.github.io/error-handling-spring-boot-starter/current/) 
to provide input validation and/or RESTful exception handling in a horizontal manner.

## Example API v1

Making the following request:

```
POST /v1.0/hoover/instructions
```
```json
{
  "roomSize" : [5, 5],
  "coords" : [1, 2, 3],
  "patches" : [
    [1, -1],
    [2, 2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEfsdESWNWW"
}
```

will return an error response:


```json
{
  "code": "VALIDATION_FAILED",
  "message": "There was a validation failure.",
  "fieldErrors": [
    {
      "code": "VALUE_SHOULD_BE_POSITIVE_OR_ZERO",
      "message": "must be greater than or equal to 0",
      "property": "patches[0][1]",
      "rejectedValue": -1,
      "path": null
    },
    {
      "code": "INVALID_SIZE",
      "message": "size must be between 2 and 2",
      "property": "coords",
      "rejectedValue": [
        1,
        2,
        3
      ],
      "path": null
    },
    {
      "code": "INVALID_SIZE",
      "message": "size must be between 2 and 2",
      "property": "patches[1]",
      "rejectedValue": [
        2,
        2,
        2
      ],
      "path": null
    },
    {
      "code": "REGEX_PATTERN_VALIDATION_FAILED",
      "message": "must match \"[NESW]+$\"",
      "property": "instructions",
      "rejectedValue": "NNESEfsdESWNWW",
      "path": null
    }
  ]
}
```


## Example API v2

Making the following request:

```
POST /v2.0/hoover/instructions
```
```json
{
  "positions" : {
    "initial" : { "x": 1, "y": 2 },
    "boundsInclusive": null,
    "dirty" : [
      { "x": 1, "y": -1 },
      { "x": 2, "y": 2 },
      { "x": 2, "y": 3 }
    ]
  },
  "instructions" : "NNESEfsdESWNWW"
}
```

will return an error response:


```json
{
  "code": "VALIDATION_FAILED",
  "message": "There was a validation failure.",
  "fieldErrors": [
    {
      "code": "REGEX_PATTERN_VALIDATION_FAILED",
      "message": "must match \"[NESW]+$\"",
      "property": "instructions",
      "rejectedValue": "NNESEfsdESWNWW",
      "path": null
    },
    {
      "code": "VALUE_SHOULD_BE_POSITIVE_OR_ZERO",
      "message": "must be greater than or equal to 0",
      "property": "positions.dirty[].y",
      "rejectedValue": -1,
      "path": null
    },
    {
      "code": "REQUIRED_NOT_NULL",
      "message": "must not be null",
      "property": "positions.boundsInclusive",
      "rejectedValue": null,
      "path": null
    }
  ]
}
```