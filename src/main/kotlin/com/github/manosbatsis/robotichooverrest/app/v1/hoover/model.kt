package com.github.manosbatsis.robotichooverrest.app.v1.hoover

import jakarta.validation.constraints.Min

interface GridPoint {
    val x: Int
    val y: Int
}

data class RoomDimensions(
    @field:Min(1)
    override val x: Int,
    @field:Min(1)
    override val y: Int
) : GridPoint {
    val maxPositionX = x - 1;
    val maxPositionY = y - 1;
}


data class HooverPosition(
    @field:Min(0)
    val x: Int,
    @field:Min(0)
    val y: Int
)

enum class CardinalDirection {
    N, S, E, W;

    companion object {
        fun valueOf(char: Char) = valueOf(char.toString())
    }
}

