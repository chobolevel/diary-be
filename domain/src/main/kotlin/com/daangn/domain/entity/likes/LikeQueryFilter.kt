package com.daangn.domain.entity.likes

import com.daangn.domain.entity.likes.QLike.like
import com.querydsl.core.types.dsl.BooleanExpression

data class LikeQueryFilter(
    private val userId: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            userId?.let { like.user.id.eq(it) },
            like.deleted.isFalse
        ).toTypedArray()
    }
}
