package com.scrimmers.domain.entity.team.join

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.team.join.QTeamJoinRequest.teamJoinRequest

data class TeamJoinRequestQueryFilter(
    private val teamId: String?,
    private val userId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            teamId?.let { teamJoinRequest.team.id.eq(it) },
            userId?.let { teamJoinRequest.user.id.eq(it) },
            teamJoinRequest.deleted.isFalse
        ).toTypedArray()
    }
}
