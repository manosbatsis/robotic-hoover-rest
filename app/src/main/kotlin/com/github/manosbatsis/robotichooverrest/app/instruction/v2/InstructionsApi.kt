package com.github.manosbatsis.robotichooverrest.app.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(InstructionsRequest.BASEPATH)
interface InstructionsApi {

    @Operation(
        operationId = "instructions_v2.0",
        summary = "Process instructions",
        description = "Process the given hoover instructions request and provide a report as response",
        tags = ["v2"]
    )
    @PostMapping("instructions")
    fun processInstructions(
        @RequestBody @Valid @NotNull input: InstructionsRequest?
    ): InstructionsResponse
}