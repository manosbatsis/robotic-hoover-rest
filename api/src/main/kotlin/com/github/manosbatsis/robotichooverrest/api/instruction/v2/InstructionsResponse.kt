package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.GridPosition
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
    @Schema(name = "InstructionsResponseV2.Position")
    data class Positions(
        @field:NotNull
        @field:Valid
        val final: GridPosition,
        @field:NotNull
        @field:Valid
        val cleaned: List<GridPosition>,
        @field:NotNull
        @field:Valid
        val effectiveInstructions: List<GridPosition>,
    )
}