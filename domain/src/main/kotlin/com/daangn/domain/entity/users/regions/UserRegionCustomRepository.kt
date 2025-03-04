package com.daangn.domain.entity.users.regions

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.regions.QUserRegion.userRegion
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserRegionCustomRepository : QuerydslRepositorySupport(UserRegion::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<UserRegion> {
        return from(userRegion)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(
        booleanExpressions: Array<BooleanExpression>,
    ): Long {
        return from(userRegion)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
