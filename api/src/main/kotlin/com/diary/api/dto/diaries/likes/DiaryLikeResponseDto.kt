package com.diary.api.dto.diaries.likes

import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DiaryLikeResponseDto(
    val id: ID,
    val diary: DiaryResponseDto,
    val user: UserResponseDto,
    val createdAt: Long,
    val updatedAt: Long
)
