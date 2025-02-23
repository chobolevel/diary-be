package com.daangn.domain.entity.users

import com.daangn.domain.entity.users.QUser.user
import com.querydsl.core.types.dsl.BooleanExpression

data class UserQueryFilter(
    private val signUpType: UserSignUpType?,
    private val role: UserRoleType?,
    private val resigned: Boolean?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            signUpType?.let { user.signUpType.eq(it) },
            role?.let { user.role.eq(it) },
            resigned?.let { user.resigned.eq(it) }
        ).toTypedArray()
    }
}
