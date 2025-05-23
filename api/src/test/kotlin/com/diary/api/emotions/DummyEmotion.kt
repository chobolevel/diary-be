package com.diary.api.emotions

import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionUpdateMask
import com.diary.domain.type.ID

object DummyEmotion {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "기분 좋음"
    private val icon: String = "\uD83D\uDE0A"
    private val order: Int = 0
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyEmotion: Emotion by lazy {
        Emotion(
            id = id,
            name = name,
            icon = icon,
            order = order
        )
    }
    private val dummyEmotionResponse: EmotionResponseDto by lazy {
        EmotionResponseDto(
            id = id,
            name = name,
            icon = icon,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    private val createRequest: CreateEmotionRequestDto by lazy {
        CreateEmotionRequestDto(
            name = name,
            icon = icon,
            order = order
        )
    }
    private val updateRequest: UpdateEmotionRequestDto by lazy {
        UpdateEmotionRequestDto(
            name = "보통",
            icon = null,
            order = null,
            updateMask = listOf(EmotionUpdateMask.NAME)
        )
    }

    fun toEntity(): Emotion {
        return dummyEmotion
    }
    fun toResponseDto(): EmotionResponseDto {
        return dummyEmotionResponse
    }
    fun toCreateRequestDto(): CreateEmotionRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateEmotionRequestDto {
        return updateRequest
    }
}
