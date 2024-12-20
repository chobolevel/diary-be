package com.scrimmers.domain.entity.scrim.request

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.scrim.request.QScrimRequest.scrimRequest

data class ScrimRequestQueryFilter(
    private val fromTeamId: String?,
    private val toTeamId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            fromTeamId?.let { scrimRequest.fromTeam.id.eq(it) },
            toTeamId?.let { scrimRequest.toTeam.id.eq(it) },
            scrimRequest.deleted.isFalse
        ).toTypedArray()
    }
}
