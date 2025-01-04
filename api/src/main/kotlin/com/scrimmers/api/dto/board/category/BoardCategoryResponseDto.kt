package com.scrimmers.api.dto.board.category

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class BoardCategoryResponseDto(
    val id: String,
    val code: String,
    val name: String,
    val order: Int,
    val createdAt: Long,
    val updatedAt: Long,
)
