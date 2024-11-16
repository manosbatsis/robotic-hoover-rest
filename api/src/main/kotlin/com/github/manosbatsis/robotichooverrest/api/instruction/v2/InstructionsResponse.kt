/*
 * Copyright (C) 2024 Manos Batsis

 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
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
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

@Schema(name = "InstructionsResponseV2")
data class InstructionsResponse(
    @field:NotNull @field:Valid val positions: Positions,
    @field:NotNull @field:PositiveOrZero val cleanedCount: Int
) {
    @Schema(name = "InstructionsResponseV2.Position")
    data class Positions(
        @field:NotNull @field:Valid val final: GridPosition,
        @field:NotNull @field:Valid val cleaned: List<GridPosition>,
        @field:NotNull @field:Valid val effectiveInstructions: List<GridPosition>,
    )
}
