package com.daangn.api.security

import com.daangn.api.dto.auth.JwtResponseDto
import com.daangn.api.properties.JwtProperties
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.PolicyException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
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

    fun generateTokens(authentication: Authentication): JwtResponseDto {
        val now = Date()
        val accessTokenExpiredAt = Date(now.time + TimeUnit.HOURS.toMillis(1))
        val refreshTokenExpiredAt = Date(now.time + TimeUnit.DAYS.toMillis(30))
        val accessToken = generateToken(
            authentication = authentication,
            issuedAt = now,
            expiredAt = accessTokenExpiredAt
        )
        val refreshToken = generateToken(
            authentication = authentication,
            issuedAt = now,
            expiredAt = refreshTokenExpiredAt
        )
        return JwtResponseDto(
            tokenType = jwtProperties.tokenPrefix,
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt.toInstant().toEpochMilli(),
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt.toInstant().toEpochMilli(),
        )
    }

    private fun generateToken(authentication: Authentication, issuedAt: Date, expiredAt: Date): String {
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.issuer)
            .setIssuedAt(issuedAt)
            .setExpiration(expiredAt)
            .setSubject(authentication.name)
            .claim("authorities", authentication.authorities)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
            .compact()
    }

    fun validateToken(token: String) {
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.secret)
                .parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw PolicyException(
                errorCode = ErrorCode.EXPIRED_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = ErrorCode.EXPIRED_TOKEN.message
            )
        } catch (e: JwtException) {
            throw PolicyException(
                errorCode = ErrorCode.INVALID_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = ErrorCode.INVALID_TOKEN.message
            )
        }
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)
            .body
        val userDetails = userDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(userDetails, token, userDetails.authorities)
    }
}
