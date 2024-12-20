package com.scrimmers.domain.entity.scrim

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.scrim.QScrim.scrim
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ScrimCustomRepository : QuerydslRepositorySupport(Scrim::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Scrim> {
        return from(scrim)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(booleanExpressions: Array<BooleanExpression>): Long {
        return from(scrim)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
