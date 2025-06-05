package com.diary.domain.entity.users.points

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.points.QUserPointHistory.userPointHistory
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class UserPointHistoryRepositoryWrapper(
    private val repository: UserPointHistoryRepository,
    private val customRepository: UserPointHistoryCustomRepository
) {

    fun save(userPointHistory: UserPointHistory): UserPointHistory {
        return repository.save(userPointHistory)
    }

    fun search(
        queryFilter: UserPointHistoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserPointHistoryOrderType>
    ): List<UserPointHistory> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    private fun List<UserPointHistoryOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                UserPointHistoryOrderType.CREATED_AT_ASC -> userPointHistory.createdAt.asc()
                UserPointHistoryOrderType.CREATED_AT_DESC -> userPointHistory.createdAt.desc()
            }
        }.toTypedArray()
    }
}
