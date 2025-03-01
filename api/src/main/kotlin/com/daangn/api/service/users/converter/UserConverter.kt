package com.daangn.api.service.users.converter

import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UserResponseDto
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserSignUpType
import io.hypersistence.tsid.TSID
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    fun convert(request: CreateUserRequestDto): User {
        return when (request.signUpType) {
            UserSignUpType.GENERAL -> {
                User(
                    id = TSID.fast().toString(),
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    socialId = null,
                    signUpType = request.signUpType,
                    nickname = request.nickname
                )
            }
            else -> {
                User(
                    id = TSID.fast().toString(),
                    email = request.email,
                    password = null,
                    socialId = passwordEncoder.encode(request.socialId),
                    signUpType = request.signUpType,
                    nickname = request.nickname
                )
            }
        }
    }

    fun convert(entity: User): UserResponseDto {
        return UserResponseDto(
            id = entity.id,
            email = entity.email,
            signUpType = entity.signUpType,
            nickname = entity.nickname,
            role = entity.role,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<User>): List<UserResponseDto> {
        return entities.map { convert(it) }
    }
}
