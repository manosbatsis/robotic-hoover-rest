package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import jakarta.validation.constraints.PositiveOrZero

data class ValidGridPosition(
    @field:PositiveOrZero
    override val x: Int,
    @field:PositiveOrZero
    override val y: Int
): Position