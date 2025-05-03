package com.diary.api.dto.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ReissueResponseDto(
    val accessToken: String,
)
