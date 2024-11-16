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
package com.github.manosbatsis.robotichooverrest.api.instruction.v1

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import kotlin.io.path.Path

@Schema(name = "InstructionsRequestV1")
data class InstructionsRequest(
    @field:NotNull
    @field:Size(min = 2, max = 2)
    val roomSize: List<@NotNull @Positive Int>?,
    @field:NotNull
    @field:Size(min = 2, max = 2)
    val coords: List<@NotNull @PositiveOrZero Int>?,
    @field:NotNull
    val patches:
        List<@Size(min = 2, max = 2) List<@NotNull @PositiveOrZero Int>>?,
    @field:Pattern(regexp = "[NESW]+\$") val instructions: String
) {
    companion object {
        const val BASEPATH = "/v1.0/hoover"
        val INSTRUCTIONS = Path(BASEPATH).resolve("instructions").toUri()
    }
}
