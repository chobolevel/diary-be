package com.diary.api.security

import com.diary.api.properties.JwtProperties
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.PolicyException
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
    private val userDetailService: UserDetailsService
) {

    fun issueTokenPair(authentication: Authentication): Pair<String, String> {
        val now = Date()
        val accessTokenExpiredAt = Date(now.time + TimeUnit.HOURS.toMillis(1))
        val refreshTokenExpiredAt = Date(now.time + TimeUnit.DAYS.toMillis(30))
        val accessToken = generateToken(
            authentication = authentication,
            issuedAt = now,
            expiredAt = accessTokenExpiredAt,
        )
        val refreshToken = generateToken(
            authentication = authentication,
            issuedAt = now,
            expiredAt = refreshTokenExpiredAt
        )
        return Pair(accessToken, refreshToken)
    }

    fun generateToken(authentication: Authentication, issuedAt: Date, expiredAt: Date): String {
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

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)
            .body
        val userDetails = userDetailService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(userDetails, token, userDetails.authorities)
    }
}
