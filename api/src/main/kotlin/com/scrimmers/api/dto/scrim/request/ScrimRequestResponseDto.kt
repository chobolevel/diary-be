package com.scrimmers.api.dto.scrim.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.domain.entity.team.scrim.request.ScrimRequestStatus

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ScrimRequestResponseDto(
    val id: String,
    val fromTeam: TeamResponseDto,
    val toTeam: TeamResponseDto,
    val status: ScrimRequestStatus,
    val comment: String,
    val rejectReason: String?,
    val createdAt: Long,
    val updatedAt: Long
)
