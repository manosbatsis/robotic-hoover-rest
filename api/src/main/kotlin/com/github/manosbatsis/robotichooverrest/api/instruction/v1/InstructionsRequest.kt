package com.github.manosbatsis.robotichooverrest.api.instruction.v1

import jakarta.validation.constraints.*
import kotlin.io.path.Path

data class InstructionsRequest(
    @field:NotNull
    @field:Size(min = 2, max = 2)
    val roomSize: List<@Positive Int>,
    @field:NotNull
    @field:Size(min = 2, max = 2)
    val coords: List<@PositiveOrZero Int>,
    @field:NotNull
    val patches: List<@Size(min = 2, max = 2) List<@PositiveOrZero Int>>,
    @field:Pattern(regexp = "[NESW]+\$")
    val instructions: String
){
    companion object {
        const val BASEPATH = "/v1.0/hoover"
        val INSTRUCTIONS = Path(BASEPATH).resolve("instructions").toUri()
    }
}