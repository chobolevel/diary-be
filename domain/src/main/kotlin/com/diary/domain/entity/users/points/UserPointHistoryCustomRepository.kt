package com.diary.domain.entity.users.points

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.points.QUserPointHistory.userPointHistory
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserPointHistoryCustomRepository : QuerydslRepositorySupport(UserPointHistory::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<UserPointHistory> {
        return from(userPointHistory)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(booleanExpressions: Array<BooleanExpression>): Long {
        return from(userPointHistory)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
