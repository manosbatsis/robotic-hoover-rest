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

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.manosbatsis.robotichooverrest.api.instruction.GridPosition
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InputGridPosition
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest
import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsResponse
import com.github.manosbatsis.robotichooverrest.client.instruction.ApiErrorResponseException
import com.github.manosbatsis.robotichooverrest.client.instruction.ClientConfiguration
import com.github.manosbatsis.robotichooverrest.client.instruction.v2.InstructionsApiClient
import java.net.URI
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.cloud.loadbalancer.ribbon.enabled=false"])
@EnableFeignClients(basePackageClasses = [InstructionsApiClient::class, ClientConfiguration::class])
class InstructionsApiV2ClientTests {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(InstructionsApiV2ClientTests::class.java)
    }

    @Autowired lateinit var applicationContext: ServletWebServerApplicationContext
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var instructionsApiClient: InstructionsApiClient

    private fun getBaseUrl() =
        URI.create(
            "http://localhost:${applicationContext.webServer.port}${InstructionsRequest.BASEPATH}")

    @Test
    fun testAssignmentSample() {
        val instructionsRequest =
            InstructionsRequest(
                positions =
                    InstructionsRequest.Positions(
                        initial = InputGridPosition(1, 2),
                        boundsInclusive = InputGridPosition(4, 4),
                        dirty =
                            setOf(
                                InputGridPosition(1, 0),
                                InputGridPosition(2, 2),
                                InputGridPosition(2, 3))),
                instructions = "NNESEESWNWW")

        val expectedResult =
            InstructionsResponse(
                positions =
                    InstructionsResponse.Positions(
                        final = GridPosition(1, 3),
                        cleaned = listOf(GridPosition(2, 3)),
                        effectiveInstructions =
                            listOf(
                                GridPosition(1, 3),
                                GridPosition(2, 3),
                                GridPosition(3, 3),
                                GridPosition(3, 2),
                                GridPosition(4, 2),
                                GridPosition(4, 3),
                                GridPosition(3, 3),
                                GridPosition(2, 3),
                                GridPosition(2, 4),
                                GridPosition(1, 4),
                                GridPosition(1, 3),
                                GridPosition(1, 2))),
                1)
        val actualResult =
            instructionsApiClient.processInstructions(getBaseUrl(), instructionsRequest)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testValidationWorks() {
        val instructionsRequest =
            InstructionsRequest(
                positions =
                    InstructionsRequest.Positions(
                        initial = InputGridPosition(1, 0),
                        boundsInclusive = null,
                        dirty =
                            setOf(
                                InputGridPosition(1, -1),
                                InputGridPosition(2, 2),
                                InputGridPosition(2, 3))),
                instructions = "NNESEfsdESWNWW")

        val responseError =
            assertThrows(
                    ApiErrorResponseException::class.java,
                    {
                        instructionsApiClient.processInstructions(getBaseUrl(), instructionsRequest)
                    },
                    "Response should be an ApiErrorResponse object")
                .apiErrorResponse

        assertEquals("There was a validation failure.", responseError.message)
    }
}
