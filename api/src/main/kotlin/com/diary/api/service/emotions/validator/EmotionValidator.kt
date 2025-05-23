package com.diary.api.service.emotions.validator

import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.emotions.EmotionUpdateMask
import org.springframework.stereotype.Component

@Component
class EmotionValidator {

    fun validate(request: CreateEmotionRequestDto) {
        request.order?.validateIsSmallerThan(
            compareTo = 0,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateEmotionRequestDto) {
        request.updateMask.forEach {
            when (it) {
                EmotionUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                EmotionUpdateMask.ICON -> {
                    request.icon.validateIsNull(parameterName = "icon")
                }
                EmotionUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 0,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
