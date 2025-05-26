package com.diary.api.service.diaries.converter

import com.diary.api.dto.diaries.likes.DiaryLikeResponseDto
import com.diary.api.service.users.converter.UserConverter
import com.diary.domain.entity.diaries.likes.DiaryLike
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class DiaryLikeConverter(
    private val diaryConverter: DiaryConverter,
    private val userConverter: UserConverter
) {

    fun convert(): DiaryLike {
        return DiaryLike(
            id = TSID.fast().toString()
        )
    }

    fun convert(entity: DiaryLike): DiaryLikeResponseDto {
        return DiaryLikeResponseDto(
            id = entity.id,
            diary = diaryConverter.convert(entity = entity.diary!!),
            user = userConverter.convert(entity.user!!),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<DiaryLike>): List<DiaryLikeResponseDto> {
        return entities.map { convert(it) }
    }
}
