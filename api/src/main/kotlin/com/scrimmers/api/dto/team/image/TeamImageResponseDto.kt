package com.scrimmers.api.dto.team.image

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.image.TeamImageType

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TeamImageResponseDto(
    val id: String,
    val type: TeamImageType,
    val name: String,
    val url: String,
    val createdAt: Long,
    val updatedAt: Long
)
