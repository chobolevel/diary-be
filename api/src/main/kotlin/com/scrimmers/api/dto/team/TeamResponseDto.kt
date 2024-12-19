package com.scrimmers.api.dto.team

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.team.image.TeamImageResponseDto

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TeamResponseDto(
    val id: String,
    val ownerId: String,
    val ownerNickname: String,
    val name: String,
    val description: String,
    val headCount: Int,
    val maxHeadCount: Int,
    val logo: TeamImageResponseDto?,
    val createdAt: Long,
    val updatedAt: Long,
)
