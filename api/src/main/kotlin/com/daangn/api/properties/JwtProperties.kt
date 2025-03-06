package com.daangn.api.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val issuer: String,
    val secret: String,
    val tokenPrefix: String,
    val refreshTokenCacheKey: String
)
