package com.diary.api.diaries

import com.diary.domain.entity.diaries.Diary
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

    fun toEntity(): Diary {
        return dummyDiary
    }
}
