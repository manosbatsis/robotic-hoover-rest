package com.github.manosbatsis.robotichooverrest.api.instruction

import jakarta.validation.constraints.PositiveOrZero

/**
 * A [Position] implementation with valid,
 * i.e. non-null coordinates.
 */
data class GridPosition(
    @field:PositiveOrZero
    override val x: Int,
    @field:PositiveOrZero
    override val y: Int
): Position