package com.diary.api.service.diaries.validator

import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateLengthSmallerThan
import com.diary.domain.entity.diaries.DiaryUpdateMask
import org.springframework.stereotype.Component

@Component
class DiaryValidator {

    fun validate(request: CreateDiaryRequestDto) {
        request.title.validateLengthSmallerThan(
            length = 10,
            parameterName = "title"
        )
        request.content.validateLengthSmallerThan(
            length = 20,
            parameterName = "content"
        )
    }

    fun validate(request: UpdateDiaryRequestDto) {
        request.updateMask.forEach {
            when (it) {
                DiaryUpdateMask.WEATHER -> {
                    request.weatherId.validateIsNull(parameterName = "weather_id")
                }
                DiaryUpdateMask.EMOTION -> {
                    request.emotionId.validateIsNull(parameterName = "emotion_id")
                }
                DiaryUpdateMask.TITLE -> {
                    request.title.validateLengthSmallerThan(
                        length = 10,
                        parameterName = "title"
                    )
                }
                DiaryUpdateMask.CONTENT -> {
                    request.content.validateLengthSmallerThan(
                        length = 20,
                        parameterName = "content"
                    )
                }
                DiaryUpdateMask.IS_SECRET -> {
                    request.isSecret.validateIsNull(parameterName = "is_secret")
                }
            }
        }
    }
}
