package com.github.manosbatsis.robotichooverrest.api.instruction.v2

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import kotlin.io.path.Path


@Schema(name = "InstructionsRequestV2")
data class InstructionsRequest(
    @field:NotNull
    @field:Valid
    val positions: Positions? = null,
    @field:NotNull
    @field:Pattern(regexp = "[NESW]+\$")
    val instructions: String? = null
){
    companion object {
        const val BASEPATH = "/v2.0/hoover"
        val INSTRUCTIONS = Path(BASEPATH).resolve("instructions").toUri()
    }

    @Schema(name = "InstructionsRequestV2.Positions")
    data class Positions(
        @field:NotNull
        @field:Valid
        val initial: InputGridPosition? = null,
        @field:NotNull
        @field:Valid
        val boundsInclusive: InputGridPosition? = null,
        @field:NotNull
        @field:Valid
        val dirty: Set<InputGridPosition>? = null,
    )
}