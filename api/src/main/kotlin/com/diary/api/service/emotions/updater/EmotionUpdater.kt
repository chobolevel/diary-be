package com.diary.api.service.emotions.updater

import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionUpdateMask
import org.springframework.stereotype.Component

@Component
class EmotionUpdater {

    fun markAsUpdate(
        request: UpdateEmotionRequestDto,
        entity: Emotion
    ): Emotion {
        request.updateMask.forEach {
            when (it) {
                EmotionUpdateMask.NAME -> entity.name = request.name!!
                EmotionUpdateMask.ICON -> entity.icon = request.icon!!
                EmotionUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
