package com.github.manosbatsis.robotichooverrest.app.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.domain.instruction.CardinalDirection
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverState
import org.springframework.web.bind.annotation.RestController

@RestController("InstructionsControllerV2")
class InstructionsController : InstructionsApi {
    override fun processInstructions(input: InstructionsRequest?): InstructionsResponse {
        // Create our robotic hoover
        val positions = input!!.positions!!
        val robot = HooverState(
            initialPosition = positions.initial!!.valid(),
            maxPosition = positions.boundsInclusive!!.valid(),
            dirtyPositions = positions.dirty!!.map { it.valid() }.toMutableSet()
        )
        // Drive it per instructions
        input.instructions!!.toCharArray().forEach { robot.move(CardinalDirection.valueOf(it)) }
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