package com.diary.api.dto.common

data class JwtResponseDto(
    val tokenType: String? = "Bearer",
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long,
)
