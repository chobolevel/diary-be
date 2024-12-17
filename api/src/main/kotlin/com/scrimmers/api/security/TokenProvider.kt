package com.scrimmers.api.security

import com.scrimmers.api.dto.jwt.JwtResponseDto
import com.scrimmers.api.properties.JwtProperties
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

@Component
class TokenProvider(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun generateToken(authentication: Authentication): JwtResponseDto {
        val now = Date()
        val accessTokenExpiredAt = Date(now.time + TimeUnit.HOURS.toMillis(1))
        val refreshTokenExpiredAt = Date(now.time + TimeUnit.DAYS.toMillis(30))
        val accessToken = issueToken(
            issuedAt = now,
            expiredAt = accessTokenExpiredAt,
            authentication = authentication
        )
        val refreshToken = issueToken(
            issuedAt = now,
            expiredAt = refreshTokenExpiredAt,
            authentication = authentication
        )
        return JwtResponseDto(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt.toInstant().toEpochMilli(),
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt.toInstant().toEpochMilli(),
        )
    }

    fun getAuthentication(token: String): Authentication? {
        return try {
            validateToken(token)
            val claims = Jwts.parser()
                .setSigningKey(jwtProperties.secret)
                .parseClaimsJws(token)
                .body
            val userDetails = userDetailsService.loadUserByUsername(claims.subject)
            UsernamePasswordAuthenticationToken(userDetails, token, userDetails.authorities)
        } catch (e: Exception) {
            logger.warn("Token is invalid", e)
            null
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(jwtProperties.secret)
                .parseClaimsJws(token)
            true
        } catch (e: ExpiredJwtException) {
            throw PolicyException(
                errorCode = ErrorCode.EXPIRED_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = "토큰이 만료되었습니다."
            )
        } catch (e: JwtException) {
            throw PolicyException(
                errorCode = ErrorCode.INVALID_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = "유효하지 않은 토큰입니다."
            )
        }
    }

    private fun issueToken(issuedAt: Date, expiredAt: Date, authentication: Authentication): String {
        return Jwts
            .builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.issuer)
            .setIssuedAt(issuedAt)
            .setExpiration(expiredAt)
            .setSubject(authentication.name)
            .claim("authorities", authentication.authorities)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
            .compact()
    }
}
