package com.daangn.api.dto.likes

import com.daangn.domain.entity.likes.LikeType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LikeRequestDto(
    @field:NotNull
    val type: LikeType,
    @field:NotEmpty
    val targetId: String,
)
