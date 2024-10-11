package com.github.manosbatsis.robotichooverrest.app.v1.api

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ApiErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

data class InstructionsRequest(
    @field:Size(min = 2, max = 2)
    val roomSize: List<@Min(1) Int>,
    @field:Size(min = 2, max = 2)
    val coords: List<@Min(0) Int>,
    val patches: List<@Size(min = 2, max = 2) List<@Min(0) Int>>,
    @field:Pattern(regexp = "[NESW]+\$")
    val instructions: String
)

data class InstructionsResponse(
    @field:Size(min = 2, max = 2)
    val coords: List<@Min(0) Int>,
    @field:Min(0)
    val patches: Int
)

@RequestMapping(HooverApi.PATH)
interface HooverApi {

    companion object {
        const val PATH = "/v1.0/hoover/instructions"
    }

    @Operation(
        summary = "Process instructions",
        description = "Process the given instructions request and provide a report response",
        tags = ["v1"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = arrayOf(Content(schema = Schema(implementation = InstructionsResponse::class)))
            ),
            ApiResponse(
                description = "Invalid request",
                content = arrayOf(Content(schema = Schema(implementation = ApiErrorResponse::class)))
            )
        ]
    )
    @PostMapping
    fun processInstructions(
        @RequestBody @Valid @NotNull input: InstructionsRequest
    ): InstructionsResponse
}