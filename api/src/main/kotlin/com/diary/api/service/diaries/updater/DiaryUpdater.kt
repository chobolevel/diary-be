package com.diary.api.service.diaries.updater

import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryUpdateMask
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionRepositoryWrapper
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherRepositoryWrapper
import org.springframework.stereotype.Component

@Component
class DiaryUpdater(
    private val weatherRepositoryWrapper: WeatherRepositoryWrapper,
    private val emotionRepositoryWrapper: EmotionRepositoryWrapper,
) {

    fun markAsUpdate(
        request: UpdateDiaryRequestDto,
        entity: Diary
    ): Diary {
        request.updateMask.forEach {
            when (it) {
                DiaryUpdateMask.WEATHER -> {
                    val weather: Weather = weatherRepositoryWrapper.findById(id = request.weatherId!!)
                    entity.set(weather = weather)
                }
                DiaryUpdateMask.EMOTION -> {
                    val emotion: Emotion = emotionRepositoryWrapper.findById(id = request.emotionId!!)
                    entity.set(emotion = emotion)
                }
                DiaryUpdateMask.TITLE -> entity.title = request.title!!
                DiaryUpdateMask.CONTENT -> entity.content = request.content!!
                DiaryUpdateMask.IS_SECRET -> entity.isSecret = request.isSecret!!
            }
        }
        return entity
    }
}
