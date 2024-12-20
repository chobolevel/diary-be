package com.scrimmers.api.dto.scrim.match.side

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.domain.entity.team.scrim.match.side.ScrimMatchSideType

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ScrimMatchSideResponseDto(
    val id: String,
    val team: TeamResponseDto,
    val side: ScrimMatchSideType,
    val killScore: Int,
    val totalGold: Int,
    val isWinSide: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
