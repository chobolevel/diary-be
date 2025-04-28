package com.diary.api.service.users.converter

import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.domain.entity.users.User
import io.hypersistence.tsid.TSID
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun convert(request: CreateUserRequestDto): User {
        return User(
            id = TSID.fast().toString(),
            username = request.username,
            password = passwordEncoder.encode(request.password),
            signUpType = request.signUpType,
            nickname = request.nickname,
            scope = request.scope
        )
    }

    fun convert(entity: User): UserResponseDto {
        return UserResponseDto(
            id = entity.id,
            username = entity.username,
            signUpType = entity.signUpType,
            signUpTypeLabel = entity.signUpType.desc,
            nickname = entity.nickname,
            scope = entity.scope,
            scopeLabel = entity.scope.desc,
            isResigned = entity.resigned,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<User>): List<UserResponseDto> {
        return entities.map { convert(it) }
    }
}
