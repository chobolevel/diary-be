package com.diary.api.dto.weathers

import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class WeatherResponseDto(
    val id: ID,
    val name: String,
    val icon: String,
    val order: Int,
    val createdAt: Long,
    val updatedAt: Long
)
