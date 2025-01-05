package com.scrimmers.api.dto.board

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.board.category.BoardCategoryResponseDto
import com.scrimmers.api.dto.user.UserResponseDto

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class BoardResponseDto(
    val id: String,
    val category: BoardCategoryResponseDto,
    val writer: UserResponseDto,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
)
