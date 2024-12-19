package com.scrimmers.domain.entity.team.leave

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.team.leave.QTeamLeaveRequest.teamLeaveRequest

data class TeamLeaveRequestQueryFilter(
    private val teamId: String?,
    private val userId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            teamId?.let { teamLeaveRequest.team.id.eq(it) },
            userId?.let { teamLeaveRequest.user.id.eq(it) },
            teamLeaveRequest.deleted.isFalse
        ).toTypedArray()
    }
}
