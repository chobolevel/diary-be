package com.scrimmers.api.dto.team.leave

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateTeamLeaveRequestDto(
    @field:NotEmpty(message = "팀 정보는 필수 값입니다.")
    val teamId: String,
    @field:NotEmpty(message = "팀 탈퇴시 사유는 필수 값입니다.")
    val comment: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateTeamLeaveRequestDto(
    val comment: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<TeamLeaveRequestUpdateMask>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RejectTeamLeaveRequestDto(
    @field:NotEmpty(message = "거절 사유는 필수 값입니다.")
    val rejectReason: String
)
