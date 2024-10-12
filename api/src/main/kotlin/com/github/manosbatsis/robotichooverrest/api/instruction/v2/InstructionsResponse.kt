package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.ValidGridPosition
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

@Schema(name = "InstructionsResponseV2")
data class InstructionsResponse(
    @field:NotNull
    @field:Valid
    val positions: Positions,
    @field:NotNull
    @field:PositiveOrZero
    val cleanedCount: Int
){
    data class Positions(
        @field:NotNull
        @field:Valid
        val final: ValidGridPosition,
        @field:NotNull
        @field:Valid
        val cleaned: List<ValidGridPosition>,
        @field:NotNull
        @field:Valid
        val effectiveInstructions: List<ValidGridPosition>,
    )
}