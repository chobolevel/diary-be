package com.diary.api.dto.users

import com.diary.domain.entity.users.UserScopeType
import com.diary.domain.entity.users.UserSignUpType
import com.diary.domain.entity.users.UserUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserRequestDto(
    @field:NotEmpty
    val username: String,
    @field:NotEmpty
    val password: String,
    @field:NotNull
    val signUpType: UserSignUpType,
    @field:NotEmpty
    val nickname: String,
    @field:NotNull
    val scope: UserScopeType
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateUserRequestDto(
    val nickname: String?,
    val scope: UserScopeType?,
    @field:Size(min = 1)
    val updateMask: List<UserUpdateMask>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChangeUserPasswordRequestDto(
    @field:NotEmpty
    val curPassword: String,
    @field:NotEmpty
    val newPassword: String
)
