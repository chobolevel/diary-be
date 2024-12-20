package com.scrimmers.api.dto.scrim.match

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.scrim.match.side.ScrimMatchSideResponseDto
import com.scrimmers.domain.entity.scrim.match.ScrimMatchWinnerSide

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ScrimMatchResponseDto(
    val id: String,
    val winnerSide: ScrimMatchWinnerSide,
    val blueSide: ScrimMatchSideResponseDto,
    val redSide: ScrimMatchSideResponseDto,
    val createdAt: Long,
    val updatedAt: Long,
)
