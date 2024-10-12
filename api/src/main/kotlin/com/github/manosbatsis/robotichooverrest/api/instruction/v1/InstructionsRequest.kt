package com.github.manosbatsis.robotichooverrest.api.instruction.v1

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import kotlin.io.path.Path

@Schema(name = "InstructionsRequestV1")
data class InstructionsRequest(
    @field:NotNull
    @field:Size(min = 2, max = 2)
    val roomSize: List<@NotNull @Positive Int>,
    @field:NotNull
    @field:Size(min = 2, max = 2)
    val coords: List<@NotNull @PositiveOrZero Int>,
    @field:NotNull
    val patches: List<@Size(min = 2, max = 2) List<@NotNull @PositiveOrZero Int>>,
    @field:Pattern(regexp = "[NESW]+\$")
    val instructions: String
){
    companion object {
        const val BASEPATH = "/v1.0/hoover"
        val INSTRUCTIONS = Path(BASEPATH).resolve("instructions").toUri()
    }
}