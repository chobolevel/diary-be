package com.diary.domain.entity.diaries

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.QDiary.diary
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class DiaryCustomRepository : QuerydslRepositorySupport(Diary::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Diary> {
        return from(diary)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(booleanExpressions: Array<BooleanExpression>): Long {
        return from(diary)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
