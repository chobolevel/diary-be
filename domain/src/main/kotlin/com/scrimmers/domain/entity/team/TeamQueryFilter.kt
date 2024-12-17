package com.scrimmers.domain.entity.team

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.team.QTeam.team

data class TeamQueryFilter(
    private val ownerId: String?,
    private val name: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            ownerId?.let { team.owner.id.eq(it) },
            name?.let { team.name.eq(it) },
            team.deleted.isFalse
        ).toTypedArray()
    }
}
