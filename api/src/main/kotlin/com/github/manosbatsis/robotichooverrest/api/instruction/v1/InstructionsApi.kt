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
package com.github.manosbatsis.robotichooverrest.api.instruction.v1

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ApiErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(InstructionsRequest.BASEPATH)
interface InstructionsApi {

    @Operation(
        operationId = "instructionsv_1.0",
        summary = "Process instructions",
        description =
            "Process the given hoover instructions request and provide a report as response",
        tags = ["v1"])
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content =
                        arrayOf(
                            Content(
                                schema = Schema(implementation = InstructionsResponse::class)))),
                ApiResponse(
                    description = "Unsuccessful operation",
                    content =
                        arrayOf(
                            Content(schema = Schema(implementation = ApiErrorResponse::class))))])
    @PostMapping("instructions")
    fun processInstructions(
        @RequestBody @Valid @NotNull input: InstructionsRequest?
    ): InstructionsResponse
}
