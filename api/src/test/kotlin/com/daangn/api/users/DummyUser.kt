package com.daangn.api.users

import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRoleType
import com.daangn.domain.entity.users.UserSignUpType
import io.hypersistence.tsid.TSID

object DummyUser {
    private val id: String = TSID.fast().toString()
    private val email: String = "rodaka123@naver.com"
    private val password: String? = "rkddlswo218@"
    private val socialId: String? = null
    private val signUpType: UserSignUpType = UserSignUpType.GENERAL
    private val nickname: String = "알감자"
    private val role: UserRoleType = UserRoleType.ROLE_USER
    private val createdAt = 0L
    private val updatedAt = 0L

    fun toEntity(): User {
        return User(
            id = id,
            email = email,
            password = password,
            socialId = socialId,
            signUpType = signUpType,
            nickname = nickname,
        )
    }
}
