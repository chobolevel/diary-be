package com.diary.api.diaries.images

import com.diary.domain.entity.diaries.images.DiaryImage
import com.diary.domain.type.ID

object DummyDiaryImage {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "flow.jpeg"
    private val width: Int = 1000
    private val height: Int = 700
    private val url: String = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyDiaryImage: DiaryImage by lazy {
        DiaryImage(
            id = id,
            name = name,
            width = width,
            height = height,
            url = url,
            order = order
        )
    }

    fun toEntity(): DiaryImage {
        return dummyDiaryImage
    }
}
