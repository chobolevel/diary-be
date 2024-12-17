package com.scrimmers.api.dto.team

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.user.UserResponseDto

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TeamResponseDto(
    val id: String,
    val owner: UserResponseDto,
    val name: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long,
)
