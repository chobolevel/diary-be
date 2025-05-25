package com.diary.domain.entity.diaries.likes

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.likes.QDiaryLike.diaryLike
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class DiaryLikeCustomRepository : QuerydslRepositorySupport(DiaryLike::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<DiaryLike> {
        return from(diaryLike)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(
        booleanExpressions: Array<BooleanExpression>
    ): Long {
        return from(diaryLike)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
