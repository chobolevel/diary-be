package com.diary.domain.entity.users.points

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.points.QUserPoint.userPoint
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class UserPointRepositoryWrapper(
    private val repository: UserPointRepository,
    private val customRepository: UserPointCustomRepository
) {

    fun save(userPoint: UserPoint): UserPoint {
        return repository.save(userPoint)
    }

    fun search(
        queryFilter: UserPointQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserPointOrderType>
    ): List<UserPoint> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(queryFilter: UserPointQueryFilter): Long {
        return customRepository.count(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    private fun List<UserPointOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                UserPointOrderType.CREATED_AT_ASC -> userPoint.createdAt.asc()
                UserPointOrderType.CREATED_AT_DESC -> userPoint.createdAt.desc()
            }
        }.toTypedArray()
    }
}
