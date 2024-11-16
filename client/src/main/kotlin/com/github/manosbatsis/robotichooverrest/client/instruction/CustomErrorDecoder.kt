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
package com.github.manosbatsis.robotichooverrest.client.instruction

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Response
import feign.codec.ErrorDecoder
import io.github.wimdeblauwe.errorhandlingspringbootstarter.ApiErrorResponse
import org.springframework.stereotype.Component

@Component
class CustomErrorDecoder(val objectMapper: ObjectMapper) : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response): Exception {
        val apiErrorResponse =
            response.body().asInputStream().use {
                objectMapper.readValue(it, ApiErrorResponse::class.java)
            }
        return ApiErrorResponseException(apiErrorResponse)
    }
}
