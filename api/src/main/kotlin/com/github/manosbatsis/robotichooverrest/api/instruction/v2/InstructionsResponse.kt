package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import com.github.manosbatsis.robotichooverrest.domain.instruction.GridPosition
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

data class InstructionsResponse(
    @field:NotNull
    val positions: Positions,
    @field:PositiveOrZero
    val cleanedCount: Int
){
    data class Positions(
        @field:NotNull
        val final: GridPosition,
        @field:NotNull
        val cleaned: List<GridPosition>,
        @field:NotNull
        val effectiveInstructions: List<GridPosition>,
    )
}