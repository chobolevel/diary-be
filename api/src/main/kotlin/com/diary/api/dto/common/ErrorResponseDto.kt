package com.diary.api.dto.common

import com.diary.domain.exception.ErrorCode
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ErrorResponseDto(
    val errorCode: ErrorCode,
    val errorMessage: String,
)
