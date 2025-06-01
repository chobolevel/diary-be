package com.diary.api.dto.diaries

import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DiaryResponseDto(
    val id: ID,
    val writer: UserResponseDto,
    val weather: WeatherResponseDto,
    val emotion: EmotionResponseDto,
    val title: String,
    val content: String,
    val isSecret: Boolean,
    val likeCount: Long,
    val createdAt: Long,
    val updatedAt: Long
)
