package com.diary.api.dto.diaries

import com.diary.domain.entity.diaries.DiaryUpdateMask
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateDiaryRequestDto(
    @field:NotEmpty
    val weatherId: ID,
    @field:NotEmpty
    val emotionId: ID,
    @field:NotEmpty
    val title: String,
    @field:NotEmpty
    val content: String,
    @field:NotNull
    val isSecret: Boolean
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateDiaryRequestDto(
    val weatherId: ID?,
    val emotionId: ID?,
    val title: String?,
    val content: String?,
    val isSecret: Boolean?,
    @field:Size(min = 1)
    val updateMask: List<DiaryUpdateMask>
)
