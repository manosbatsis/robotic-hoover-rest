package com.github.manosbatsis.robotichooverrest.api.instruction.v1

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

@Schema(name = "InstructionsResponseV1")
data class InstructionsResponse(
    @field:Size(min = 2, max = 2)
    val coords: List<@PositiveOrZero Int>,
    @field:Min(0)
    val patches: Int
)