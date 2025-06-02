package com.diary.api.dto.diaries.images

import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DiaryImageResponseDto(
    val id: ID,
    val name: String,
    val width: Int,
    val height: Int,
    val url: String,
    val order: Int,
    val createdAt: Long,
    val updatedAt: Long
)
