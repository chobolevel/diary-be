package com.scrimmers.api.service.user

import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.dto.user.CreateUserRequestDto
import com.scrimmers.api.dto.user.UpdateUserRequestDto
import com.scrimmers.api.dto.user.UserResponseDto
import com.scrimmers.api.service.user.converter.UserConverter
import com.scrimmers.api.service.user.updater.UserUpdater
import com.scrimmers.api.service.user.validator.UserValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.entity.user.UserOrderType
import com.scrimmers.domain.entity.user.UserQueryFilter
import com.scrimmers.domain.entity.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val repository: UserRepository,
    private val finder: UserFinder,
    private val validator: UserValidator,
    private val converter: UserConverter,
    private val updater: UserUpdater
) {

    @Transactional
    fun create(request: CreateUserRequestDto): String {
        validator.validate(request)
        val user = converter.convert(request)
        return repository.save(user).id
    }

    @Transactional(readOnly = true)
    fun getUsers(
        queryFilter: UserQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserOrderType>?
    ): PaginationResponseDto {
        val users = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = users.map { converter.convert(it) },
        )
    }

    @Transactional(readOnly = true)
    fun getUser(id: String): UserResponseDto {
        val user = finder.findById(id)
        return converter.convert(user)
    }

    @Transactional
    fun update(requesterId: String, request: UpdateUserRequestDto): String {
        validator.validate(request)
        val user = finder.findById(requesterId)
        updater.markAsUpdate(request = request, entity = user)
        return user.id
    }

    @Transactional
    fun resign(requesterId: String): Boolean {
        val user = finder.findById(requesterId)
        user.resign()
        return true
    }
}
