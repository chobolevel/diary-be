package com.diary.api.diaries.likes

import com.diary.api.diaries.DummyDiary
import com.diary.api.dto.diaries.likes.DiaryLikeResponseDto
import com.diary.api.users.DummyUser
import com.diary.domain.entity.diaries.likes.DiaryLike
import com.diary.domain.type.ID

object DummyDiaryLike {
    private val id: ID = "0KH4WDSJA2CHB"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyDiaryLike: DiaryLike by lazy {
        DiaryLike(
            id = id
        )
    }
    private val dummyDiaryLikeResponse: DiaryLikeResponseDto by lazy {

        DiaryLikeResponseDto(
            id = id,
            diary = DummyDiary.toResponseDto(),
            user = DummyUser.toResponseDto(),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun toEntity(): DiaryLike {
        return dummyDiaryLike
    }
    fun toResponseDto(): DiaryLikeResponseDto {
        return dummyDiaryLikeResponse
    }
}
