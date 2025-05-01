package com.diary.api.dto.users

import com.diary.domain.entity.users.UserRoleType
import com.diary.domain.entity.users.UserScopeType
import com.diary.domain.entity.users.UserSignUpType
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserResponseDto(
    val id: ID,
    val username: String,
    val signUpType: UserSignUpType,
    val signUpTypeLabel: String,
    val nickname: String,
    val scope: UserScopeType,
    val scopeLabel: String,
    val role: UserRoleType,
    val roleLabel: String,
    val isResigned: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
