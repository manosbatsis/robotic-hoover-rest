package com.github.manosbatsis.robotichooverrest.app.instruction.v2

import com.github.manosbatsis.robotichooverrest.api.instruction.v2.InstructionsRequest.Companion.INSTRUCTIONS
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
class InstructionsApiV2Tests {
	companion object {
		private val log: Logger = LoggerFactory.getLogger(this::class.java)
	}

	@Autowired
	private lateinit var mockMvc: MockMvc
	@Test
	fun testAssignmentSample() {
		val requestJson = """
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

		val result = mockMvc.perform(post(INSTRUCTIONS).contentType(APPLICATION_JSON)
			.content(requestJson))
			.andExpect(status().isOk())
			.andReturn()

		val json = result.response.contentAsString
		assertEquals("{\"positions\":{\"final\":{\"x\":1,\"y\":3},\"cleaned\":[{\"x\":2,\"y\":3}],\"effectiveInstructions\":[{\"x\":1,\"y\":3},{\"x\":2,\"y\":3},{\"x\":3,\"y\":3},{\"x\":3,\"y\":2},{\"x\":4,\"y\":2},{\"x\":4,\"y\":3},{\"x\":3,\"y\":3},{\"x\":2,\"y\":3},{\"x\":2,\"y\":4},{\"x\":1,\"y\":4},{\"x\":1,\"y\":3},{\"x\":1,\"y\":2}]},\"cleanedCount\":1}",
			json)
	}

}
