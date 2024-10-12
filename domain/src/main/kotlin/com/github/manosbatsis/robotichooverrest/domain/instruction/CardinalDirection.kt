package com.github.manosbatsis.robotichooverrest.domain.instruction

enum class CardinalDirection {
    N, S, E, W;

    companion object {
        fun valueOf(char: Char) = valueOf(char.toString())
    }
}