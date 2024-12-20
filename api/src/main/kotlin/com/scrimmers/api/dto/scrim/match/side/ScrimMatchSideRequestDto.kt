package com.scrimmers.api.dto.scrim.match.side

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.scrim.match.side.ScrimMatchSideUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateScrimMatchSideRequestDto(
    @field:NotEmpty(message = "팀 정보는 필수 값입니다.")
    val teamId: String,
    @field:NotNull(message = "킬 스코어는 필수 값입니다.")
    val killScore: Int,
    @field:NotNull(message = "총 골드는 필수 값입니다.")
    val totalGold: Int
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateScrimMatchSideRequestDto(
    val killScore: Int?,
    val totalGold: Int?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<ScrimMatchSideUpdateMask>
)
