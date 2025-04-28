package com.diary.api.users

import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserScopeType
import com.diary.domain.entity.users.UserSignUpType
import com.diary.domain.entity.users.UserUpdateMask
import com.diary.domain.type.ID

object DummyUser {
    private val id: ID = "0KH4WDSJA2CHB"
    private val username: String = "rodaka123"
    private val password: String = "rkddlswo218@"
    private val signUpType: UserSignUpType = UserSignUpType.GENERAL
    private val nickname: String = "알감자"
    private val scope: UserScopeType = UserScopeType.PUBLIC
    private val resigned: Boolean = false
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

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
    private val userResponse: UserResponseDto by lazy {
        UserResponseDto(
            id = id,
            username = username,
            signUpType = signUpType,
            signUpTypeLabel = signUpType.desc,
            nickname = nickname,
            scope = scope,
            scopeLabel = scope.desc,
            isResigned = resigned,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val createRequest: CreateUserRequestDto by lazy {
        CreateUserRequestDto(
            username = username,
            password = password,
            signUpType = signUpType,
            nickname = nickname,
            scope = scope
        )
    }
    private val updateRequest: UpdateUserRequestDto by lazy {
        UpdateUserRequestDto(
            nickname = "닉네임변경",
            scope = null,
            updateMask = listOf(UserUpdateMask.NICKNAME)
        )
    }
    private val changePasswordRequest: ChangeUserPasswordRequestDto by lazy {
        ChangeUserPasswordRequestDto(
            curPassword = "rkddlswo218@",
            newPassword = "jik584697@"
        )
    }

    fun toEntity(): User {
        return user
    }
    fun toResponseDto(): UserResponseDto {
        return userResponse
    }
    fun toCreateRequestDto(): CreateUserRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateUserRequestDto {
        return updateRequest
    }
    fun toChangePasswordRequestDto(): ChangeUserPasswordRequestDto {
        return changePasswordRequest
    }
}
