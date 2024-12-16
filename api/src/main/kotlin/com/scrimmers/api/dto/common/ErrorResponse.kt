package com.scrimmers.api.dto.common

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.exception.ErrorCode

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ErrorResponse(
    val errorCode: ErrorCode,
    val errorMessage: String
)
