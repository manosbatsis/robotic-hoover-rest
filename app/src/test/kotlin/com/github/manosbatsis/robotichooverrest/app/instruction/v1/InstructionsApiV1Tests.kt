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
package com.github.manosbatsis.robotichooverrest.app.instruction.v1

import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsRequest
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
class InstructionsApiV1Tests {
    companion object {
        private val log: Logger =
            LoggerFactory.getLogger(InstructionsApiV1Tests::class.java)
    }

    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun testAssignmentSample() {
        val requestJson =
            """
            {
                "roomSize" : [5, 5],
                "coords" : [1, 2],
                "patches" : [
                    [1, 0],
                    [2, 2],
                    [2, 3]
                ],
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
        assertEquals("{\"coords\":[1,3],\"patches\":1}", json)
    }

    @Test
    fun testValidationWorks() {
        val requestJson =
            """
            {
                "roomSize" : [5, 5],
                "coords" : [1, 2, 3],
                "patches" : [
                    [1, -1],
                    [2, 2, 2],
                    [2, 3]
                ],
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
