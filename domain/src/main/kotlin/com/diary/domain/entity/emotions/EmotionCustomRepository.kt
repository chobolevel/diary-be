package com.diary.domain.entity.emotions

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.emotions.QEmotion.emotion
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EmotionCustomRepository : QuerydslRepositorySupport(Emotion::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>,
    ): List<Emotion> {
        return from(emotion)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(
        booleanExpressions: Array<BooleanExpression>,
    ): Long {
        return from(emotion)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
