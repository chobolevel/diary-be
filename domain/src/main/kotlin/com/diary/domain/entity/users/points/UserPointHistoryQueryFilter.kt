package com.diary.domain.entity.users.points

import com.diary.domain.entity.users.points.QUserPointHistory.userPointHistory
import com.diary.domain.type.ID
import com.querydsl.core.types.dsl.BooleanExpression

data class UserPointHistoryQueryFilter(
    private val userId: ID?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            userId?.let { userPointHistory.user.id.eq(it) },
            userPointHistory.deleted.isFalse
        ).toTypedArray()
    }
}
