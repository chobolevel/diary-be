package com.diary.api.dto.common

data class JwtResponseDto(
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long,
)
