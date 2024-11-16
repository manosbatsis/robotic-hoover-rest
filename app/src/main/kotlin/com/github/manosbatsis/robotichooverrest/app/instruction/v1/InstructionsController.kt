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
package com.github.manosbatsis.robotichooverrest.app.instruction.v1

import com.github.manosbatsis.robotichooverrest.api.instruction.GridPosition
import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.domain.instruction.CardinalDirection
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverState
import org.springframework.web.bind.annotation.RestController

@RestController("InstructionsControllerV1")
class InstructionsController : InstructionsApi {
    override fun processInstructions(
        input: InstructionsRequest?
    ): InstructionsResponse {
        // Create our robotic hoover
        val robot =
            HooverState(
                initialPosition =
                    GridPosition(
                        x = input!!.coords!!.first(),
                        y = input.coords!!.last()),
                maxPosition =
                    GridPosition(
                        x = input.roomSize!!.first() - 1,
                        y = input!!.roomSize!!.last() - 1),
                dirtyPositions =
                    input.patches!!.map {
                        GridPosition(x = it.first(), y = it.last())
                    })
        // Drive it per instructions
        input.instructions.toCharArray().forEach {
            robot.move(CardinalDirection.valueOf(it))
        }
        // Build and send the response
        return InstructionsResponse(
            coords = listOf(robot.currentPosition.x, robot.currentPosition.y),
            patches = robot.cleanedPatches.size)
    }
}
