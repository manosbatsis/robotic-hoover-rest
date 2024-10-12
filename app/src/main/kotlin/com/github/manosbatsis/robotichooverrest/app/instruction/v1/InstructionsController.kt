package com.github.manosbatsis.robotichooverrest.app.instruction.v1

import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.domain.instruction.CardinalDirection
import com.github.manosbatsis.robotichooverrest.domain.instruction.GridPosition
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverState
import org.springframework.web.bind.annotation.RestController

@RestController
class InstructionsController : InstructionsApi {
    override fun processInstructions(input: InstructionsRequest): InstructionsResponse {
        // Create our robotic hoover
        val robot = HooverState(
            initialPosition = GridPosition(x = input.coords.first(), y = input.coords.last()),
            maxPosition = GridPosition(x = input.roomSize.first() - 1, y = input.roomSize.last() - 1),
            dirtyPositions = input.patches.map { GridPosition(x = it.first(), y = it.last()) }.toMutableSet()
        )
        // Drive it per instructions
        input.instructions.toCharArray().forEach { robot.move(CardinalDirection.valueOf(it)) }
        // Build and send the response
        return InstructionsResponse(
            coords = listOf(robot.currentPosition.x, robot.currentPosition.y),
            patches = robot.cleanedPatches.size
        )
    }
}