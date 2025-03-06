package com.daangn.api.auth

import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.ReissueRequestDto
import com.daangn.domain.entity.users.UserSignUpType

object DummyAuth {
    private val email: String = "rodaka123@naver.com"
    private val password: String = "rkddlswo218@"
    private val socialId: String? = null
    private val signUpType: UserSignUpType = UserSignUpType.GENERAL

    fun toLoginRequestDto(): LoginRequestDto {
        return loginRequest
    }
    fun toReissueRequestDto(): ReissueRequestDto {
        return reissueRequest
    }

    private val loginRequest: LoginRequestDto by lazy {
        LoginRequestDto(
            email = email,
            password = password,
            socialId = socialId,
            signUpType = signUpType
        )
    }
    private val reissueRequest: ReissueRequestDto by lazy {
        ReissueRequestDto(
            refreshToken = "refresh-token"
        )
    }
}
