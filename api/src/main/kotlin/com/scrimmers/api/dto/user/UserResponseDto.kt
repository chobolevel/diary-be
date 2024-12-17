package com.scrimmers.api.dto.user

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.user.image.UserImageResponseDto
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserRoleType

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserResponseDto(
    val id: String,
    val email: String,
    val loginType: UserLoginType,
    val nickname: String,
    val phone: String,
    val role: UserRoleType,
    val profileImage: UserImageResponseDto?,
    val createdAt: Long,
    val updatedAt: Long
)
