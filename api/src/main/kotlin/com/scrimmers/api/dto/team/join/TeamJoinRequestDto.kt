package com.scrimmers.api.dto.team.join

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.join.TeamJoinRequestUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateTeamJoinRequestDto(
    @field:NotEmpty(message = "팀 정보는 필수 값입니다.")
    var teamId: String,
    @field:NotEmpty(message = "팀 합류 요청을 위한 소개글은 필수 값입니다.")
    val comment: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateTeamJoinRequestDto(
    val comment: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<TeamJoinRequestUpdateMask>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RejectTeamJoinRequestDto(
    @field:NotEmpty(message = "거절 사유는 필수 값입니다.")
    val reason: String
)
