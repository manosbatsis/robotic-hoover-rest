package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import jakarta.validation.constraints.PositiveOrZero

/**
 * A [Position] implementation with valid,
 * i.e. non-null coordinates.
 */
data class ValidGridPosition(
    @field:PositiveOrZero
    override val x: Int,
    @field:PositiveOrZero
    override val y: Int
): Position