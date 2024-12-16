package com.scrimmers.domain.entity.user

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.user.QUser.user

data class UserQueryFilter(
    val loginType: UserLoginType?,
    val role: UserRoleType?,
    val resigned: Boolean?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            loginType?.let { user.loginType.eq(it) },
            role?.let { user.role.eq(it) },
            resigned?.let { user.resigned.eq(it) }
        ).toTypedArray()
    }
}
