package com.diary.api.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val issuer: String,
    val secret: String,
    val tokenPrefix: String,
    val accessTokenKey: String,
    val refreshTokenKey: String,
    val refreshTokenCacheKey: String
)
