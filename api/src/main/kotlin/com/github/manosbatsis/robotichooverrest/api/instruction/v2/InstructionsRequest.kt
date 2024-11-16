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

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import kotlin.io.path.Path

@Schema(name = "InstructionsRequestV2")
data class InstructionsRequest(
    @field:NotNull @field:Valid val positions: Positions? = null,
    @field:NotNull
    @field:Pattern(regexp = "[NESW]+\$")
    val instructions: String? = null
) {
    companion object {
        const val BASEPATH = "/v2.0/hoover"
        val INSTRUCTIONS = Path(BASEPATH).resolve("instructions").toUri()
    }

    @Schema(name = "InstructionsRequestV2.Positions")
    data class Positions(
        @field:NotNull @field:Valid val initial: InputGridPosition? = null,
        @field:NotNull
        @field:Valid
        val boundsInclusive: InputGridPosition? = null,
        @field:NotNull @field:Valid val dirty: Set<InputGridPosition>? = null,
    )
}
