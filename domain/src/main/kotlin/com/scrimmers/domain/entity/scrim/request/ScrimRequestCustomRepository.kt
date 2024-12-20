package com.scrimmers.domain.entity.scrim.request

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.scrim.request.QScrimRequest.scrimRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ScrimRequestCustomRepository : QuerydslRepositorySupport(ScrimRequest::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<ScrimRequest> {
        return from(scrimRequest)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(booleanExpressions: Array<BooleanExpression>): Long {
        return from(scrimRequest)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
