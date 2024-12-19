package com.scrimmers.api.dto.team

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.TeamUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateTeamRequestDto(
    @field:NotEmpty(message = "팅 이름은 필수 값입니다.")
    val name: String,
    @field:NotEmpty(message = "팀 설명은 필수 값입니다.")
    val description: String,
    @field:NotNull(message = "팀 최대 인원수는 필수 값입니다.")
    val maxHeadCount: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateTeamRequestDto(
    val name: String?,
    val description: String?,
    val maxHeadCount: Int?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<TeamUpdateMask>
)
