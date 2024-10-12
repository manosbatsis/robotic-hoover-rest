package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

@Schema(name = "InstructionsResponseV2")
data class InstructionsResponse(
    @field:NotNull
    val positions: Positions,
    @field:PositiveOrZero
    val cleanedCount: Int
){
    data class Positions(
        @field:NotNull
        val final: ValidGridPosition,
        @field:NotNull
        val cleaned: List<ValidGridPosition>,
        @field:NotNull
        val effectiveInstructions: List<ValidGridPosition>,
    )
}