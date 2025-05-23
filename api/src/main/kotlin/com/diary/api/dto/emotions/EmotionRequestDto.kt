package com.diary.api.dto.emotions

import com.diary.domain.entity.emotions.EmotionUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateEmotionRequestDto(
    @field:NotEmpty
    val name: String,
    @field:NotEmpty
    val icon: String,
    val order: Int?
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateEmotionRequestDto(
    val name: String?,
    val icon: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<EmotionUpdateMask>
)
