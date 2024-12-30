package com.scrimmers.api.service.auth

import com.scrimmers.api.dto.auth.LoginRequestDto
import com.scrimmers.api.dto.jwt.JwtResponseDto
import com.scrimmers.api.security.CustomAuthenticationManager
import com.scrimmers.api.security.TokenProvider
import com.scrimmers.api.service.auth.validator.AuthValidator
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val tokenProvider: TokenProvider,
    private val customAuthenticationManager: CustomAuthenticationManager,
    private val redisTemplate: RedisTemplate<String, String>,
    private val validator: AuthValidator,
    private val userFinder: UserFinder
) {

    private val opsForHash = redisTemplate.opsForHash<String, String>()

    fun login(request: LoginRequestDto): JwtResponseDto {
        validator.validate(request)
        val authenticationToken = when (request.loginType) {
            UserLoginType.GENERAL -> {
                UsernamePasswordAuthenticationToken(
                    "${request.email}/${request.loginType}",
                    request.password
                )
            }

            else -> {
                UsernamePasswordAuthenticationToken(
                    "${request.email}/${request.loginType}",
                    request.socialId
                )
            }
        }
        val authentication = customAuthenticationManager.authenticate(authenticationToken)
        return tokenProvider.generateToken(authentication).also {
            setRefreshToken(
                userId = authentication.name,
                refreshToken = it.refreshToken
            )
        }
    }

    fun reissueToken(refreshToken: String): JwtResponseDto {
        val authentication = tokenProvider.getAuthentication(refreshToken) ?: throw PolicyException(
            errorCode = ErrorCode.INVALID_TOKEN,
            status = HttpStatus.UNAUTHORIZED,
            message = "토큰이 만료되었습니다. 재로그인 해주세요."
        )
        val user = userFinder.findById(authentication.name)
        val cachedRefreshToken = opsForHash.get("refresh-token:v1", user.id)
        if (cachedRefreshToken == null || cachedRefreshToken != refreshToken) {
            throw PolicyException(
                errorCode = ErrorCode.INVALID_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = "유효하지 않은 갱신 토큰입니다. 재로그인 해주세요."
            )
        }
        val result = tokenProvider.generateToken(authentication)
        return result
    }

    private fun setRefreshToken(userId: String, refreshToken: String) {
        opsForHash.put("refresh-token:v1", userId, refreshToken)
    }
}
