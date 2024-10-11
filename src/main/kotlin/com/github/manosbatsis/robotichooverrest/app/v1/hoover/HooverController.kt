package com.github.manosbatsis.robotichooverrest.app.v1.hoover

import com.github.manosbatsis.robotichooverrest.app.v1.api.HooverApi
import com.github.manosbatsis.robotichooverrest.app.v1.api.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.app.v1.api.InstructionsResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class HooverController : HooverApi {
    override fun processInstructions(input: InstructionsRequest): InstructionsResponse {
        // Create our robotic hoover
        val robot = HooverState.of(
            position = HooverPosition(x = input.coords.first(), y = input.coords.last()),
            room = RoomDimensions(x = input.roomSize.first(), y = input.roomSize.last()),
            dirtyPatches = input.patches.map { HooverPosition(x = it.first(), y = it.last()) }.toSet()
        )
        // Drive it per instructions
        input.instructions.toCharArray().forEach { robot.move(CardinalDirection.valueOf(it)) }
        // Build and send the response
        return InstructionsResponse(
            coords = listOf(robot.position.x, robot.position.y),
            patches = robot.cleanedPatches
        )
    }
}