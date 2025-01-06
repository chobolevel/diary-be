package com.scrimmers.api.dto.team.leave

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.api.dto.user.UserResponseDto
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestStatus

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TeamLeaveRequestResponseDto(
    val id: String,
    val team: TeamResponseDto,
    val requester: UserResponseDto,
    val status: TeamLeaveRequestStatus,
    val comment: String,
    val rejectReason: String?,
    val createdAt: Long,
    val updatedAt: Long
)
