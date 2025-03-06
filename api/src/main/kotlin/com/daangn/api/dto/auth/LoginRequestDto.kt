package com.daangn.api.dto.auth

import com.daangn.domain.entity.users.UserSignUpType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LoginRequestDto(
    @field:NotEmpty
    val email: String,
    val password: String?,
    val socialId: String?,
    @field:NotNull
    val signUpType: UserSignUpType
)
