package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.Position
import com.github.manosbatsis.robotichooverrest.api.instruction.ValidGridPosition
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

/**
 * A [Position] implementation with nullable coordinates
 * to properly utilise Bean Validation.
 */
data class InputGridPosition(
    @field:NotNull
    @field:PositiveOrZero
    override val x: Int?,
    @field:NotNull
    @field:PositiveOrZero
    override val y: Int?
): Position {

    fun valid() = ValidGridPosition(
        x = x ?: error("Source member x is null"),
        y = y ?: error("Source member y is null"),
    )
}

