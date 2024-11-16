/*
 * Copyright (C) 2024 Manos Batsis

 * Foobar is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with Foobar. If not, see
 * <https://www.gnu.org/licenses/>.
 */
package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.GridPosition
import com.github.manosbatsis.robotichooverrest.api.instruction.Position
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

/**
 * A [Position] implementation with nullable coordinates to properly utilise
 * Bean Validation.
 */
data class InputGridPosition(
    @field:NotNull @field:PositiveOrZero override val x: Int?,
    @field:NotNull @field:PositiveOrZero override val y: Int?
) : Position {

    fun gridPosition() =
        GridPosition(
            x = x ?: error("Source member x is null"),
            y = y ?: error("Source member y is null"),
        )
}
