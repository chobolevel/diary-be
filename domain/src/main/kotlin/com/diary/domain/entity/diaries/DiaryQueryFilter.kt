package com.diary.domain.entity.diaries

import com.diary.domain.entity.diaries.QDiary.diary
import com.diary.domain.type.ID
import com.querydsl.core.types.dsl.BooleanExpression

data class DiaryQueryFilter(
    private val writerId: ID?,
    private val weatherId: ID?,
    private val emotionId: ID?,
    private val title: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            writerId?.let { diary.writer.id.eq(it) },
            weatherId?.let { diary.weather.id.eq(it) },
            emotionId?.let { diary.emotion.id.eq(it) },
            title?.let { diary.title.startsWith(it) },
            diary.deleted.isFalse
        ).toTypedArray()
    }
}
