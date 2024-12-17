package com.scrimmers.api.dto.jwt

data class JwtResponseDto(
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long,
)
