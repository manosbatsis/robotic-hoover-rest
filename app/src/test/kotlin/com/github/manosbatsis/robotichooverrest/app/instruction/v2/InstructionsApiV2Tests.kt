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
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(value = [InstructionsController::class])
class InstructionsApiV2Tests {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(InstructionsApiV2Tests::class.java)
    }

    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun testAssignmentSample() {
        val requestJson =
            """
            {
                "positions" : {
                   "initial" : { "x": 1, "y": 2 },
                   "boundsInclusive": { "x": 4, "y": 4 },
                   "dirty" : [
                      { "x": 1, "y": 0 },
                      { "x": 2, "y": 2 },
                      { "x": 2, "y": 3 }
                   ]
                },
                "instructions" : "NNESEESWNWW"
            }
        """

        val result =
            mockMvc
                .perform(
                    post(InstructionsRequest.INSTRUCTIONS)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()

        val json = result.response.contentAsString
        assertEquals(
            "{\"positions\":{\"final\":{\"x\":1,\"y\":3},\"cleaned\":[{\"x\":2,\"y\":3}],\"effectiveInstructions\":[{\"x\":1,\"y\":3},{\"x\":2,\"y\":3},{\"x\":3,\"y\":3},{\"x\":3,\"y\":2},{\"x\":4,\"y\":2},{\"x\":4,\"y\":3},{\"x\":3,\"y\":3},{\"x\":2,\"y\":3},{\"x\":2,\"y\":4},{\"x\":1,\"y\":4},{\"x\":1,\"y\":3},{\"x\":1,\"y\":2}]},\"cleanedCount\":1}",
            json)
    }

    @Test
    fun testValidationWorks() {
        val requestJson =
            """
            {
                "positions" : {
                   "initial" : { "x": 1, "y": 2 },
                   "boundsInclusive": null,
                   "dirty" : [
                      { "x": 1, "y": -1 },
                      { "x": 2, "y": 2 },
                      { "x": 2, "y": 3 }
                   ]
                },
                "instructions" : "NNESEfsdESWNWW"
            }
        """

        val result =
            mockMvc
                .perform(
                    post(InstructionsRequest.INSTRUCTIONS)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is4xxClientError)
                .andReturn()

        val json = result.response.contentAsString
        log.info("Error response: {}", json)
    }
}
