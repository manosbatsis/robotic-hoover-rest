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
package com.github.manosbatsis.robotichooverrest.app.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverInstructionsCommand
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverRobotState

/** Helper component to convert between data object types */
class InstructionsMapper {

    fun instructionsResponse(hooverState: HooverRobotState) =
        InstructionsResponse(
            positions =
                InstructionsResponse.Positions(
                    final = hooverState.currentPosition,
                    cleaned = hooverState.cleanedPatches,
                    effectiveInstructions = hooverState.positionStack),
            cleanedCount = hooverState.cleanCount)

    fun hooverInstructionsCommand(input: InstructionsRequest): HooverInstructionsCommand {
        val positions = input.positions!!
        return HooverInstructionsCommand(
            initialPosition = positions.initial!!.gridPosition(),
            maxPosition = positions.boundsInclusive!!.gridPosition(),
            dirtyPositions = positions.dirty!!.map { it.gridPosition() },
            instructions = input.instructions!!)
    }
}
