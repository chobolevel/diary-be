package com.daangn.api.service.auth

import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.LoginResponseDto
import com.daangn.api.dto.auth.ReissueRequestDto
import com.daangn.api.dto.auth.ReissueResponseDto
import com.daangn.api.properties.JwtProperties
import com.daangn.api.security.TokenProvider
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.PolicyException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val redisTemplate: RedisTemplate<String, String>,
    private val jwtProperties: JwtProperties
) {

    fun login(request: LoginRequestDto): LoginResponseDto {
        val user = userRepositoryWrapper.findByEmail(request.email)
        when (user.signUpType) {
            UserSignUpType.GENERAL -> {
                if (!passwordEncoder.matches(request.password, user.password)) {
                    throw BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.")
                }
            }
            else -> {
                if (!passwordEncoder.matches(request.socialId, user.socialId)) {
                    throw BadCredentialsException("소셜 아이디가 일치하지 않습니다.")
                }
            }
        }
        val token = UsernamePasswordAuthenticationToken(
            user.id,
            user.password,
            AuthorityUtils.createAuthorityList(user.role.name)
        )
        val jwtResponse = tokenProvider.generateTokens(token).also {
            setRefreshToken(
                userId = user.id,
                refreshToken = it.refreshToken
            )
        }
        return LoginResponseDto(
            tokenType = jwtResponse.tokenType,
            accessToken = jwtResponse.accessToken,
            accessTokenExpiredAt = jwtResponse.accessTokenExpiredAt,
            refreshToken = jwtResponse.refreshToken,
            refreshTokenExpiredAt = jwtResponse.refreshTokenExpiredAt,
        )
    }

    fun reissue(request: ReissueRequestDto): ReissueResponseDto {
        tokenProvider.validateToken(request.refreshToken)
        val authentication = tokenProvider.getAuthentication(request.refreshToken)
        val userId: String = authentication.name
        val cachedRefreshToken = getRefreshToken(
            userId = userId
        ) ?: throw PolicyException(
            errorCode = ErrorCode.INVALID_REFRESH_TOKEN,
            status = HttpStatus.UNAUTHORIZED,
            message = ErrorCode.INVALID_REFRESH_TOKEN.message
        )
        if (request.refreshToken != cachedRefreshToken) {
            throw PolicyException(
                errorCode = ErrorCode.INVALID_REFRESH_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = ErrorCode.INVALID_REFRESH_TOKEN.message
            )
        }
        val jwtResponse = tokenProvider.generateTokens(authentication).also {
            setRefreshToken(
                userId = userId,
                refreshToken = it.refreshToken
            )
        }
        return ReissueResponseDto(
            tokenType = jwtResponse.tokenType,
            accessToken = jwtResponse.accessToken,
            accessTokenExpiredAt = jwtResponse.accessTokenExpiredAt,
            refreshToken = jwtResponse.refreshToken,
            refreshTokenExpiredAt = jwtResponse.refreshTokenExpiredAt,
        )
    }

    fun logout(userId: String): Boolean {
        removeRefreshToken(
            userId = userId
        )
        return true
    }

    private fun setRefreshToken(userId: String, refreshToken: String) {
        redisTemplate.opsForHash<String, String>().put(jwtProperties.refreshTokenCacheKey, userId, refreshToken)
    }

    private fun getRefreshToken(userId: String): String? {
        return redisTemplate.opsForHash<String, String>().get(jwtProperties.refreshTokenCacheKey, userId)
    }

    private fun removeRefreshToken(userId: String) {
        redisTemplate.opsForHash<String, String>().delete(jwtProperties.refreshTokenCacheKey, userId)
    }
}
