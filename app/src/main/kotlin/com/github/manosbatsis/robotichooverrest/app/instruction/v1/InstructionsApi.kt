package com.github.manosbatsis.robotichooverrest.app.instruction.v1

import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsRequest
import io.github.wimdeblauwe.errorhandlingspringbootstarter.ApiErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import jakarta.validation.constraints.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping(InstructionsRequest.BASEPATH)
interface InstructionsApi {

    @Operation(
        summary = "Process instructions",
        description = "Process the given hoover instructions request and provide a report as response",
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
                description = "Unsuccessful operation",
                content = arrayOf(Content(schema = Schema(implementation = ApiErrorResponse::class)))
            )
        ]
    )
    @PostMapping("instructions")
    fun processInstructions(
        @RequestBody @Valid @NotNull input: InstructionsRequest
    ): InstructionsResponse
}