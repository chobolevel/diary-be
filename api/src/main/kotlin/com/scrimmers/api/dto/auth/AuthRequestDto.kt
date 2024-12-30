package com.scrimmers.api.dto.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.user.UserLoginType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AuthRequestDto(
    @field:NotEmpty(message = "이메일은 필수 값입니다.")
    val email: String,
    val password: String?,
    val socialId: String?,
    @field:NotNull(message = "로그인 유형은 필수 값입니다.")
    var loginType: UserLoginType
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ResetPasswordRequestDto(
    @field:NotEmpty(message = "이메일은 필수 값입니다.")
    val email: String,
)
