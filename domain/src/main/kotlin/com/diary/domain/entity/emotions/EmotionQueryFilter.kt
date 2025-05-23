package com.diary.domain.entity.emotions

import com.diary.domain.entity.emotions.QEmotion.emotion
import com.querydsl.core.types.dsl.BooleanExpression

data class EmotionQueryFilter(
    private val name: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            name?.let { emotion.name.eq(it) },
            emotion.deleted.isFalse
        ).toTypedArray()
    }
}
