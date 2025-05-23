package com.diary.api.diaries

import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.emotions.DummyEmotion
import com.diary.api.users.DummyUser
import com.diary.api.weathers.DummyWeather
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryUpdateMask
import com.diary.domain.type.ID

object DummyDiary {
    private val id: ID = "0KH4WDSJA2CHB"
    private val title: String = "일기 제목"
    private val content: String = "일기 내용을 입력했습니다."
    private val isSecret: Boolean = false
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyDiary: Diary by lazy {
        Diary(
            id = id,
            title = title,
            content = content,
            isSecret = isSecret
        )
    }
    private val dummyDiaryResponse: DiaryResponseDto by lazy {
        DiaryResponseDto(
            id = id,
            writer = DummyUser.toResponseDto(),
            weather = DummyWeather.toResponseDto(),
            emotion = DummyEmotion.toResponseDto(),
            title = title,
            content = content,
            isSecret = isSecret,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    private val createRequest: CreateDiaryRequestDto by lazy {
        CreateDiaryRequestDto(
            weatherId = "0KH4WDSJA2CHB",
            emotionId = "0KH4WDSJA2CHB",
            title = title,
            content = content,
            isSecret = isSecret,
        )
    }
    private val updateRequest: UpdateDiaryRequestDto by lazy {
        UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = "변경 일기 제목",
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.TITLE)
        )
    }

    fun toEntity(): Diary {
        return dummyDiary
    }
    fun toResponseDto(): DiaryResponseDto {
        return dummyDiaryResponse
    }
    fun toCreateRequestDto(): CreateDiaryRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateDiaryRequestDto {
        return updateRequest
    }
}
