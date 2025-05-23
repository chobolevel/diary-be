package com.diary.api.emotions

import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.type.ID

object DummyEmotion {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "기분 좋음"
    private val icon: String = "\uD83D\uDE0A"
    private val order: Int = 0
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyEmotion: Emotion by lazy {
        Emotion(
            id = id,
            name = name,
            icon = icon,
            order = order
        )
    }

    fun toEntity(): Emotion {
        return dummyEmotion
    }
}
