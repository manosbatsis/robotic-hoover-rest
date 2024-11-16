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
package com.github.manosbatsis.robotichooverrest.app.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.domain.instruction.HooverInstructionsExecutor
import org.springframework.web.bind.annotation.RestController

@RestController("InstructionsControllerV2")
class InstructionsController : InstructionsApi {

    private val mapper = InstructionsMapper()
    private val executor = HooverInstructionsExecutor()

    override fun processInstructions(
        input: InstructionsRequest?
    ): InstructionsResponse {

        // Convert request input to an instructions command
        val command = mapper.hooverInstructionsCommand(input!!)

        // Execute the command and obtain the result state
        val hooverState = executor.exec(command)

        // Convert state to response and send
        return mapper.instructionsResponse(hooverState)
    }
}
