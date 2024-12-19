package com.scrimmers.domain.entity.team.scrim

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.team.scrim.QScrim.scrim

data class ScrimQueryFilter(
    private val scrimRequestId: String?,
    private val teamId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            scrimRequestId?.let { scrim.scrimRequest.id.eq(it) },
            teamId?.let { scrim.homeTeam.id.eq(it).or(scrim.awayTeam.id.eq(it)) },
            scrim.deleted.isFalse
        ).toTypedArray()
    }
}
