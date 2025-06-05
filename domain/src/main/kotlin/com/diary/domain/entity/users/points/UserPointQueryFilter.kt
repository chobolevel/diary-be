package com.diary.domain.entity.users.points

import com.diary.domain.entity.users.points.QUserPoint.userPoint
import com.diary.domain.type.ID
import com.querydsl.core.types.dsl.BooleanExpression

data class UserPointQueryFilter(
    private val userId: ID?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            userId?.let { userPoint.user.id.eq(it) },
            userPoint.deleted.isFalse
        ).toTypedArray()
    }
}
