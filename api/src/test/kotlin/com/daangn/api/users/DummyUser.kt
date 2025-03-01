package com.daangn.api.users

import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.dto.users.UserResponseDto
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRoleType
import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.entity.users.UserUpdateMask
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

    fun toCreateRequestDto(): CreateUserRequestDto {
        return createRequest
    }
    fun toEntity(): User {
        return user
    }
    fun toResponseDto(): UserResponseDto {
        return userResponse
    }
    fun toUpdateRequestDto(): UpdateUserRequestDto {
        return updateRequest
    }

    private val createRequest: CreateUserRequestDto by lazy {
        CreateUserRequestDto(
            email = "rodaka123@naver.com",
            password = "rkddlswo218@",
            socialId = null,
            signUpType = UserSignUpType.GENERAL,
            nickname = "강인재",
        )
    }
    private val user: User by lazy {
        User(
            id = id,
            email = email,
            password = password,
            socialId = socialId,
            signUpType = signUpType,
            nickname = nickname,
        )
    }
    private val userResponse: UserResponseDto by lazy {
        UserResponseDto(
            id = id,
            email = email,
            signUpType = signUpType,
            nickname = nickname,
            role = role,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val updateRequest: UpdateUserRequestDto by lazy {
        UpdateUserRequestDto(
            nickname = "변경",
            updateMask = listOf(
                UserUpdateMask.NICKNAME
            )
        )
    }
}
