package com.diary.domain.entity.users

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.QUser.user
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepository : QuerydslRepositorySupport(User::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>,
    ): List<User> {
        return from(user)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(
        booleanExpressions: Array<BooleanExpression>
    ): Long {
        return from(user)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
