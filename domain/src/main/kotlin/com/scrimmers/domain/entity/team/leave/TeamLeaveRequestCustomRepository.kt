package com.scrimmers.domain.entity.team.leave

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.leave.QTeamLeaveRequest.teamLeaveRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TeamLeaveRequestCustomRepository : QuerydslRepositorySupport(TeamLeaveRequest::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<TeamLeaveRequest> {
        return from(teamLeaveRequest)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(booleanExpressions: Array<BooleanExpression>): Long {
        return from(teamLeaveRequest)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
