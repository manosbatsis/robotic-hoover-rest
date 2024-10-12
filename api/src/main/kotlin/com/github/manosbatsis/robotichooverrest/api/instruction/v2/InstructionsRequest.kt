package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import com.github.manosbatsis.robotichooverrest.domain.instruction.GridPosition
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import kotlin.io.path.Path

data class InstructionsRequest(
    @field:NotNull
    val positions: Positions,
    @field:Pattern(regexp = "[NESW]+\$")
    val instructions: String
){
    companion object {
        const val BASEPATH = "/v2.0/hoover"
        val INSTRUCTIONS = Path(BASEPATH).resolve("instructions").toUri()
    }

    data class Positions(
        @field:NotNull
        val initial: GridPosition,
        @field:NotNull
        val boundsInclusive: GridPosition,
        @field:NotNull
        val dirty: Set<GridPosition>,
    )
}