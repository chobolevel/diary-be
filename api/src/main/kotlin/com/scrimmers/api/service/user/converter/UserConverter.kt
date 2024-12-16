package com.scrimmers.api.service.user.converter

import com.scrimmers.api.dto.user.CreateUserRequestDto
import com.scrimmers.api.dto.user.UserResponseDto
import com.scrimmers.domain.entity.user.User
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserRoleType
import io.hypersistence.tsid.TSID
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun convert(request: CreateUserRequestDto): User {
        return when (request.loginType) {
            UserLoginType.GENERAL -> {
                User(
                    id = TSID.fast().toString(),
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    socialId = null,
                    loginType = request.loginType,
                    nickname = request.nickname,
                    phone = request.phone,
                    role = UserRoleType.ROLE_USER,
                )
            }

            else -> {
                User(
                    id = TSID.fast().toString(),
                    email = request.email,
                    password = null,
                    socialId = request.socialId,
                    loginType = request.loginType,
                    nickname = request.nickname,
                    phone = request.phone,
                    role = UserRoleType.ROLE_USER,
                )
            }
        }
    }

    fun convert(entity: User): UserResponseDto {
        return UserResponseDto(
            id = entity.id,
            email = entity.email,
            loginType = entity.loginType,
            nickname = entity.nickname,
            phone = entity.phone,
            role = entity.role,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
