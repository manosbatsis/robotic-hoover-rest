package com.github.manosbatsis.robotichooverrest.app

import com.github.manosbatsis.robotichooverrest.app.v1.api.HooverApi
import com.github.manosbatsis.robotichooverrest.app.v1.hoover.HooverController
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals

@WebMvcTest(value = [HooverController::class])
class AppApplicationTests {
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

		val result = mockMvc.perform(post(HooverApi.PATH).contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
			.andExpect(status().isOk())
			.andReturn()

		val json = result.response.contentAsString
		assertEquals("{\"coords\":[1,3],\"patches\":1}", json)
	}

}
