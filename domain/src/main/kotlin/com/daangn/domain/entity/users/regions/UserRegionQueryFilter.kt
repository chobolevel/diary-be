package com.daangn.domain.entity.users.regions

import com.daangn.domain.entity.users.regions.QUserRegion.userRegion
import com.querydsl.core.types.dsl.BooleanExpression

data class UserRegionQueryFilter(
    private val userId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            userId?.let { userRegion.user.id.eq(it) },
            userRegion.deleted.isFalse
        ).toTypedArray()
    }
}
