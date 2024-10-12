package com.github.manosbatsis.robotichooverrest.app.instruction.v1

import com.github.manosbatsis.robotichooverrest.api.instruction.v1.InstructionsRequest.Companion.INSTRUCTIONS
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals

@WebMvcTest(value = [InstructionsController::class])
class InstructionsApiV1Tests {
	companion object {
		private val log: Logger = LoggerFactory.getLogger(this::class.java)
	}

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun testAssignmentSample() {
		val requestJson = """
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

		val result = mockMvc.perform(post(INSTRUCTIONS).contentType(APPLICATION_JSON)
			.content(requestJson))
			.andExpect(status().isOk())
			.andReturn()

		val json = result.response.contentAsString
		assertEquals("{\"coords\":[1,3],\"patches\":1}", json)
	}

	@Test
	fun testValidationWorks() {
		val requestJson = """
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

		val result = mockMvc.perform(post(INSTRUCTIONS).contentType(APPLICATION_JSON)
			.content(requestJson))
			.andExpect(status().isOk())
			.andReturn()

		val json = result.response.contentAsString
		assertEquals("{\"coords\":[1,3],\"patches\":1}", json)
	}

}
