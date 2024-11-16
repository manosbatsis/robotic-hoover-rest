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
package com.github.manosbatsis.robotichooverrest.domain.instruction

import com.github.manosbatsis.robotichooverrest.api.instruction.GridPosition
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverInstructionsExecutor.Command

/** Contains the instructions to create and drive our robotic hoover */
data class HooverInstructionsCommand(
    val initialPosition: GridPosition,
    val maxPosition: GridPosition,
    val dirtyPositions: Collection<GridPosition>,
    val instructions: String
) : Command<HooverRobotState> {
    override fun exec(): HooverRobotState {

        // Create our robotic hoover
        val hooverState = HooverRobotState(initialPosition, maxPosition, dirtyPositions)

        // Drive it per instructions
        instructions.toCharArray().forEach { hooverState.move(CardinalDirection.valueOf(it)) }

        return hooverState
    }
}
