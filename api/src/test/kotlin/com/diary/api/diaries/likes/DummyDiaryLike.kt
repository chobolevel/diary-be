package com.diary.api.diaries.likes

import com.diary.domain.entity.diaries.likes.DiaryLike
import com.diary.domain.type.ID

object DummyDiaryLike {
    private val id: ID = "0KH4WDSJA2CHB"

    private val dummyDiaryLike: DiaryLike by lazy {
        DiaryLike(
            id = id
        )
    }

    fun toEntity(): DiaryLike {
        return dummyDiaryLike
    }
}
