package com.daangn.api.dto.users

import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.entity.users.UserUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserRequestDto(
    @field:NotEmpty
    val email: String,
    val password: String?,
    val socialId: String?,
    @field:NotNull
    val signUpType: UserSignUpType,
    @field:NotEmpty
    val nickname: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateUserRequestDto(
    val nickname: String?,
    @field:Size(min = 1)
    val updateMask: List<UserUpdateMask>
)
