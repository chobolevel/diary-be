package com.daangn.api.dto.users

import com.daangn.domain.entity.users.UserRoleType
import com.daangn.domain.entity.users.UserSignUpType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserResponseDto(
    val id: String,
    val email: String,
    val signUpType: UserSignUpType,
    val nickname: String,
    val role: UserRoleType,
    val createdAt: Long,
    val updatedAt: Long
)
