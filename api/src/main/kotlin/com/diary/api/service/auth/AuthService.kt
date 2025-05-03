package com.diary.api.service.auth

import com.diary.api.dto.auth.LoginRequestDto
import com.diary.api.dto.auth.LoginResponseDto
import com.diary.api.dto.auth.ReissueResponseDto
import com.diary.api.properties.JwtProperties
import com.diary.api.security.TokenProvider
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.PolicyException
import com.diary.domain.type.ID
import org.springframework.data.redis.core.HashOperations
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class AuthService(
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val opsForHash: HashOperations<String, String, String>,
    private val jwtProperties: JwtProperties,
) {

    fun login(request: LoginRequestDto): LoginResponseDto {
        val user: User = userRepositoryWrapper.findByUsername(username = request.username)
        validatePasswordMatch(
            passwordEncoder = passwordEncoder,
            rawPassword = request.password,
            encodedPassword = user.password,
        )
        val token = UsernamePasswordAuthenticationToken(
            user.id,
            user.password,
            AuthorityUtils.createAuthorityList(user.role.name)
        )
        val tokenPair: Pair<String, String> = tokenProvider.issueTokenPair(authentication = token)
        cacheRefreshToken(
            opsForHash = opsForHash,
            refreshTokenCacheKey = jwtProperties.refreshTokenCacheKey,
            userId = user.id,
            refreshToken = tokenPair.second,
        )
        return LoginResponseDto(
            accessToken = tokenPair.first,
            refreshToken = tokenPair.second,
        )
    }

    fun reissue(refreshToken: String): ReissueResponseDto {
        tokenProvider.validateToken(token = refreshToken)
        val authentication: Authentication = tokenProvider.getAuthentication(token = refreshToken)
        val cachedUserId: ID? = getUserIdByRefreshToken(
            opsForHash = opsForHash,
            refreshTokenCacheKey = jwtProperties.refreshTokenCacheKey,
            refreshToken = refreshToken
        )
        validateIsCachedRefreshToken(
            authentication = authentication,
            cachedUserId = cachedUserId
        )
        val tokenPair: Pair<String, String> = tokenProvider.issueTokenPair(authentication = authentication)
        return ReissueResponseDto(
            accessToken = tokenPair.first,
        )
    }

    fun logout(refreshToken: String): Boolean {
        tokenProvider.validateToken(token = refreshToken)
        deleteRefreshToken(
            opsForHash = opsForHash,
            refreshTokenCacheKey = jwtProperties.refreshTokenCacheKey,
            refreshToken = refreshToken
        )
        return true
    }

    @Throws(BadCredentialsException::class)
    private fun validatePasswordMatch(passwordEncoder: BCryptPasswordEncoder, rawPassword: String, encodedPassword: String) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw BadCredentialsException(
                "아이디 또는 비밀번호가 일치하지 않습니다."
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateIsCachedRefreshToken(authentication: Authentication, cachedUserId: ID?) {
        if (cachedUserId == null || authentication.name != cachedUserId) {
            throw PolicyException(
                errorCode = ErrorCode.INVALID_REFRESH_TOKEN,
                message = ErrorCode.INVALID_REFRESH_TOKEN.message
            )
        }
    }

    private fun cacheRefreshToken(opsForHash: HashOperations<String, String, String>, refreshTokenCacheKey: String, userId: ID, refreshToken: String) {
        opsForHash.put(refreshTokenCacheKey, refreshToken, userId)
    }

    private fun getUserIdByRefreshToken(opsForHash: HashOperations<String, String, String>, refreshTokenCacheKey: String, refreshToken: String): ID? {
        return opsForHash.get(refreshTokenCacheKey, refreshToken)
    }

    private fun deleteRefreshToken(opsForHash: HashOperations<String, String, String>, refreshTokenCacheKey: String, refreshToken: String) {
        opsForHash.delete(refreshTokenCacheKey, refreshToken)
    }
}
