package com.scrimmers.api.dto.scrim

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.scrim.request.ScrimRequestResponseDto
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.domain.entity.team.scrim.ScrimType
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ScrimResponseDto(
    val id: String,
    val scrimRequest: ScrimRequestResponseDto,
    val homeTeam: TeamResponseDto,
    val awayTeam: TeamResponseDto,
    val type: ScrimType,
    val name: String,
    val startedAt: LocalDateTime,
    val createdAt: Long,
    val updatedAt: Long
)
