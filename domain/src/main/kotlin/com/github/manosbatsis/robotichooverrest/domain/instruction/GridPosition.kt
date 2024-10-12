package com.github.manosbatsis.robotichooverrest.domain.instruction

import jakarta.validation.constraints.PositiveOrZero

data class GridPosition(
    @field:PositiveOrZero
    val x: Int,
    @field:PositiveOrZero
    val y: Int
)