package com.diary.api.auth

import com.diary.api.dto.auth.LoginRequestDto
import com.diary.api.dto.auth.LoginResponseDto
import com.diary.api.dto.auth.ReissueResponseDto

object DummyAuth {
    private val username: String = "rodaka123"
    private val password: String = "rkddlswo218@"

    private val accessToken: String = "access-token"
    private val refreshToken: String = "refresh-token"

    private val loginRequest: LoginRequestDto by lazy {
        LoginRequestDto(
            username = username,
            password = password,
        )
    }
    private val loginResponse: LoginResponseDto by lazy {
        LoginResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
    private val reissueResponse: ReissueResponseDto by lazy {
        ReissueResponseDto(
            accessToken = accessToken,
        )
    }

    fun toLoginRequestDto(): LoginRequestDto {
        return loginRequest
    }
    fun toLoginResponseDto(): LoginResponseDto {
        return loginResponse
    }
    fun toReissueResponseDto(): ReissueResponseDto {
        return reissueResponse
    }
}
