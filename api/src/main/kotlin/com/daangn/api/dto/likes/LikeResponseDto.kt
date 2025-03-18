package com.daangn.api.dto.likes

import com.daangn.domain.entity.likes.LikeType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class LikeResponseDto(
    val id: String,
    val type: LikeType,
    val targetId: String,
    val createdAt: Long,
    val updatedAt: Long
)
