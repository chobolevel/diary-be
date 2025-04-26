package com.diary.domain.entity.users

import com.diary.domain.entity.users.QUser.user
import com.querydsl.core.types.dsl.BooleanExpression

data class UserQueryFilter(
    private val username: String?,
    private val signUpType: UserSignUpType?,
    private val nickname: String?,
    private val resigned: Boolean?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            username?.let { user.username.startsWith(it) },
            signUpType?.let { user.signUpType.eq(it) },
            nickname?.let { user.nickname.startsWith(it) },
            resigned?.let { user.resigned.eq(it) } ?: user.resigned.isFalse
        ).toTypedArray()
    }
}
