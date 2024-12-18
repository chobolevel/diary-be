package com.scrimmers.domain.entity.team.join

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.join.QTeamJoinRequest.teamJoinRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TeamJoinRequestCustomRepository : QuerydslRepositorySupport(TeamJoinRequest::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<TeamJoinRequest> {
        return from(teamJoinRequest)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(booleanExpressions: Array<BooleanExpression>): Long {
        return from(teamJoinRequest)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
