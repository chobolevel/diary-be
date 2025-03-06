package com.daangn.api.auth

import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.LoginResponseDto
import com.daangn.api.dto.auth.ReissueRequestDto
import com.daangn.api.dto.auth.ReissueResponseDto
import com.daangn.domain.entity.users.UserSignUpType

object DummyAuth {
    private val email: String = "rodaka123@naver.com"
    private val password: String = "rkddlswo218@"
    private val socialId: String? = null
    private val signUpType: UserSignUpType = UserSignUpType.GENERAL

    fun toLoginRequestDto(): LoginRequestDto {
        return loginRequest
    }
    fun toLoginResponseDto(): LoginResponseDto {
        return loginResponse
    }
    fun toReissueRequestDto(): ReissueRequestDto {
        return reissueRequest
    }
    fun toReissueResponseDto(): ReissueResponseDto {
        return reissueResponse
    }

    private val loginRequest: LoginRequestDto by lazy {
        LoginRequestDto(
            email = email,
            password = password,
            socialId = socialId,
            signUpType = signUpType
        )
    }
    private val loginResponse: LoginResponseDto by lazy {
        LoginResponseDto(
            tokenType = "Bearer",
            accessToken = "access-token",
            accessTokenExpiredAt = 0L,
            refreshToken = "refresh-token",
            refreshTokenExpiredAt = 0L,
        )
    }
    private val reissueRequest: ReissueRequestDto by lazy {
        ReissueRequestDto(
            refreshToken = "refresh-token"
        )
    }
    private val reissueResponse: ReissueResponseDto by lazy {
        ReissueResponseDto(
            tokenType = "Bearer",
            accessToken = "access-token",
            accessTokenExpiredAt = 0L,
            refreshToken = "refresh-token",
            refreshTokenExpiredAt = 0L,
        )
    }
}
