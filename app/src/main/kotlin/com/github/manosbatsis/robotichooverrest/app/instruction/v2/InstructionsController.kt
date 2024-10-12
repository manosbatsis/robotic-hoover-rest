package com.github.manosbatsis.robotichooverrest.app.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.domain.instruction.CardinalDirection
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverState
import org.springframework.web.bind.annotation.RestController

@RestController
class InstructionsController : InstructionsApi {
    override fun processInstructions(input: InstructionsRequest): InstructionsResponse {
        // Create our robotic hoover
        val robot = HooverState(
            initialPosition = input.positions.initial,
            maxPosition = input.positions.boundsInclusive,
            dirtyPositions = input.positions.dirty.toMutableSet()
        )
        // Drive it per instructions
        input.instructions.toCharArray().forEach { robot.move(CardinalDirection.valueOf(it)) }
        // Build and send the response
        return InstructionsResponse(
            positions = InstructionsResponse.Positions(
                final = robot.currentPosition,
                cleaned = robot.cleanedPatches,
                effectiveInstructions = robot.positionStack
            ),
            cleanedCount = robot.cleanCount
        )
    }
}