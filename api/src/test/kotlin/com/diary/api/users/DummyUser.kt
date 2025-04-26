package com.diary.api.users

import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserScopeType
import com.diary.domain.entity.users.UserSignUpType

object DummyUser {
    private val id: String = "0KH4WDSJA2CHB"
    private val username: String = "rodaka123"
    private val password: String = "rkddlswo218@"
    private val signUpType: UserSignUpType = UserSignUpType.GENERAL
    private val nickname: String = "알감자"
    private val scope: UserScopeType = UserScopeType.PUBLIC

    private val user: User by lazy {
        User(
            id = id,
            username = username,
            password = password,
            signUpType = signUpType,
            nickname = nickname,
            scope = scope
        )
    }

    fun toEntity(): User {
        return user
    }
}
