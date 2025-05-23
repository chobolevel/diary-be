package com.diary.api.service.emotions.converter

import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.domain.entity.emotions.Emotion
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class EmotionConverter {

    fun convert(request: CreateEmotionRequestDto): Emotion {
        return Emotion(
            id = TSID.fast().toString(),
            name = request.name,
            icon = request.icon,
            order = request.order ?: 0
        )
    }

    fun convert(entity: Emotion): EmotionResponseDto {
        return EmotionResponseDto(
            id = entity.id,
            name = entity.name,
            icon = entity.icon,
            order = entity.order,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Emotion>): List<EmotionResponseDto> {
        return entities.map { convert(it) }
    }
}
