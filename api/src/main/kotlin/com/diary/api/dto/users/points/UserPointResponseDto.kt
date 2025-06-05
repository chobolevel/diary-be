package com.diary.api.dto.users.points

import com.diary.domain.entity.users.points.UserPointType
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserPointResponseDto(
    val id: ID,
    val type: UserPointType,
    val typeLabel: String,
    val amount: Int,
    val reason: String,
    val createdAt: Long,
    val updatedAt: Long,
)
