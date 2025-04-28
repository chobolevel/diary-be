package com.diary.api.service.users

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.converter.UserConverter
import com.diary.api.service.users.updater.UserUpdater
import com.diary.api.service.users.validator.UserValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserOrderType
import com.diary.domain.entity.users.UserQueryFilter
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.PolicyException
import com.diary.domain.type.ID
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val repositoryWrapper: UserRepositoryWrapper,
    private val converter: UserConverter,
    private val updater: UserUpdater,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val validator: UserValidator,
) {

    @Transactional
    fun join(request: CreateUserRequestDto): ID {
        validator.validate(request = request)
        val user: User = converter.convert(request = request)
        return repositoryWrapper.save(user).id
    }

    fun getUsers(
        queryFilter: UserQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserOrderType>
    ): PaginationResponseDto {
        val users: List<User> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = users),
            totalCount = totalCount
        )
    }

    fun getUser(
        id: ID
    ): UserResponseDto {
        val user: User = repositoryWrapper.findById(id = id)
        return converter.convert(entity = user)
    }

    fun getMe(id: String): UserResponseDto {
        val user: User = repositoryWrapper.findById(id = id)
        return converter.convert(entity = user)
    }

    @Transactional
    fun update(id: ID, request: UpdateUserRequestDto): ID {
        validator.validate(request = request)
        val user: User = repositoryWrapper.findById(id = id)
        val updatedUser: User = updater.markAsUpdate(
            request = request,
            entity = user
        )
        return updatedUser.id
    }

    @Transactional
    fun resign(id: ID): Boolean {
        val user: User = repositoryWrapper.findById(id = id)
        user.resign()
        return user.resigned
    }

    @Transactional
    fun changePassword(id: ID, request: ChangeUserPasswordRequestDto): ID {
        validator.validate(request = request)
        val user: User = repositoryWrapper.findById(id = id)
        matches(
            rawPassword = request.curPassword,
            encodedPassword = user.password,
            passwordEncoder = passwordEncoder
        )
        user.changePassword(newEncodedPassword = passwordEncoder.encode(request.newPassword))
        return user.id
    }

    private fun matches(rawPassword: String, encodedPassword: String, passwordEncoder: BCryptPasswordEncoder) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw PolicyException(
                errorCode = ErrorCode.USER_CURRENT_PASSWORD_NOT_MATCH,
                message = ErrorCode.USER_CURRENT_PASSWORD_NOT_MATCH.message
            )
        }
    }
}
