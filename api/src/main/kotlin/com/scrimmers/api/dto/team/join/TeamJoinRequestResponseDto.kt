package com.scrimmers.api.dto.team.join

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.api.dto.user.UserResponseDto
import com.scrimmers.domain.entity.team.join.TeamJoinRequestStatus

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TeamJoinRequestResponseDto(
    val id: String,
    val team: TeamResponseDto,
    val requester: UserResponseDto,
    val status: TeamJoinRequestStatus,
    val comment: String,
    val rejectReason: String?,
    val createdAt: Long,
    val updatedAt: Long
)
