package com.diary.domain.entity.diaries.likes

import com.diary.domain.entity.diaries.likes.QDiaryLike.diaryLike
import com.diary.domain.type.ID
import com.querydsl.core.types.dsl.BooleanExpression

data class DiaryLikeQueryFilter(
    private val diaryId: ID?,
    private val userId: ID?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            diaryId?.let { diaryLike.diary.id.eq(it) },
            userId?.let { diaryLike.user.id.eq(it) },
            diaryLike.deleted.isFalse
        ).toTypedArray()
    }
}
