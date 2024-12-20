package com.scrimmers.api.dto.scrim.match

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.scrim.match.side.CreateScrimMatchSideRequestDto
import com.scrimmers.api.dto.scrim.match.side.UpdateScrimMatchSideRequestDto
import com.scrimmers.domain.entity.scrim.match.ScrimMatchUpdateMask
import com.scrimmers.domain.entity.scrim.match.ScrimMatchWinnerSide
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateScrimMatchRequestDto(
    @field:NotEmpty(message = "스크림 정보는 필수 값입니다.")
    val scrimId: String,
    @field:NotNull(message = "매치 승리 사이드는 필수 값입니다.")
    val winnerSide: ScrimMatchWinnerSide,
    @field:NotNull(message = "매치 순서는 필수 값입니다.")
    val order: Int,
    val pogUserId: String?,
    @field:NotNull(message = "매치 블루팀 정보는 필수 값입니다.")
    val blueTeam: CreateScrimMatchSideRequestDto,
    @field:NotNull(message = "매치 레드팀 정보는 필수 값입니다.")
    val redTeam: CreateScrimMatchSideRequestDto,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateScrimMatchRequestDto(
    val scrimId: String?,
    val winnerSide: ScrimMatchWinnerSide?,
    val order: Int?,
    val pogUserId: String?,
    val blueTeam: UpdateScrimMatchSideRequestDto?,
    val redTeam: UpdateScrimMatchSideRequestDto?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<ScrimMatchUpdateMask>
)
