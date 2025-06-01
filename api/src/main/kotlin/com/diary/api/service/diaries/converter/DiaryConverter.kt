package com.diary.api.service.diaries.converter

import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.service.emotions.converter.EmotionConverter
import com.diary.api.service.users.converter.UserConverter
import com.diary.api.service.weathers.converter.WeatherConverter
import com.diary.api.util.getCurrentUserId
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.likes.DiaryLikeRepositoryWrapper
import com.diary.domain.type.ID
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class DiaryConverter(
    private val userConverter: UserConverter,
    private val weatherConverter: WeatherConverter,
    private val emotionConverter: EmotionConverter,
    private val diaryLikeRepositoryWrapper: DiaryLikeRepositoryWrapper
) {

    fun convert(request: CreateDiaryRequestDto): Diary {
        return Diary(
            id = TSID.fast().toString(),
            title = request.title,
            content = request.content,
            isSecret = request.isSecret
        )
    }

    fun convert(entity: Diary): DiaryResponseDto {
        val currentUserId: ID? = getCurrentUserId()
        val isOwner: Boolean = entity.writer!!.id == currentUserId
        val (title, content) = when (entity.isSecret && !isOwner) {
            true -> Pair("비밀 일기입니다.", "비밀 일기입니다.")
            false -> Pair(entity.title, entity.content)
        }
        return DiaryResponseDto(
            id = entity.id,
            writer = userConverter.convert(entity = entity.writer!!),
            weather = weatherConverter.convert(entity = entity.weather!!),
            emotion = emotionConverter.convert(entity = entity.emotion!!),
            title = title,
            content = content,
            isSecret = entity.isSecret,
            likeCount = diaryLikeRepositoryWrapper.countByDiaryId(diaryId = entity.id),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Diary>): List<DiaryResponseDto> {
        return entities.map { convert(it) }
    }
}
