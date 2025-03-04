package com.daangn.domain.entity.users.regions

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.regions.QUserRegion.userRegion
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class UserRegionRepositoryWrapper(
    private val repository: UserRegionRepository,
    private val customRepository: UserRegionCustomRepository
) {

    fun save(userRegion: UserRegion): UserRegion {
        return repository.save(userRegion)
    }

    fun search(
        queryFilter: UserRegionQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserRegionOrderType>
    ): List<UserRegion> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: UserRegionQueryFilter,
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): UserRegion {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_REGION_NOT_FOUND,
            message = ErrorCode.USER_REGION_NOT_FOUND.message
        )
    }

    private fun List<UserRegionOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                UserRegionOrderType.CREATED_AT_ASC -> userRegion.createdAt.asc()
                UserRegionOrderType.CREATED_AT_DESC -> userRegion.createdAt.desc()
            }
        }.toTypedArray()
    }
}
