package com.diary.api.auth

import com.diary.api.dto.auth.LoginRequestDto

object DummyAuth {
    private val username: String = "rodaka123"
    private val password: String = "rkddlswo218@"

    private val loginRequest: LoginRequestDto by lazy {
        LoginRequestDto(
            username = username,
            password = password,
        )
    }

    fun toLoginRequestDto(): LoginRequestDto {
        return loginRequest
    }
}
