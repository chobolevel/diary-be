package com.daangn.api.service.users

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UserRegionResponseDto
import com.daangn.api.service.users.converter.UserRegionConverter
import com.daangn.api.service.users.updater.UserRegionUpdater
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.entity.users.regions.UserRegionOrderType
import com.daangn.domain.entity.users.regions.UserRegionQueryFilter
import com.daangn.domain.entity.users.regions.UserRegionRepositoryWrapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegionService(
    private val repositoryWrapper: UserRegionRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val converter: UserRegionConverter,
    private val updater: UserRegionUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateUserRegionRequestDto): String {
        val userRegion = converter.convert(request = request).also {
            val user = userRepositoryWrapper.findById(userId)
            it.set(user)
        }
        return repositoryWrapper.save(userRegion).id
    }

    fun getUserRegions(
        queryFilter: UserRegionQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserRegionOrderType>
    ): PaginationResponseDto {
        val userRegions = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.searchCount(
            queryFilter = queryFilter,
        )
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(userRegions),
            totalCount = totalCount
        )
    }

    fun getUserRegion(userRegionId: String): UserRegionResponseDto {
        val userRegion = repositoryWrapper.findById(userRegionId)
        return converter.convert(userRegion)
    }

    @Transactional
    fun update(userId: String, userRegionId: String, request: UpdateUserRegionRequestDto): String {
        val userRegion = repositoryWrapper.findById(userRegionId)
        updater.markAsUpdate(
            request = request,
            entity = userRegion
        )
        return userRegionId
    }

    @Transactional
    fun delete(userId: String, userRegionId: String): Boolean {
        val userRegion = repositoryWrapper.findById(userRegionId)
        userRegion.delete()
        return true
    }
}
