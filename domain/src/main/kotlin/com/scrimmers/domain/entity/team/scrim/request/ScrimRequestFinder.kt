package com.scrimmers.domain.entity.team.scrim.request

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.scrim.request.QScrimRequest.scrimRequest
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class ScrimRequestFinder(
    private val repository: ScrimRequestRepository,
    private val customRepository: ScrimRequestCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): ScrimRequest {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.SCRIM_REQUEST_IS_NOT_FOUND,
            message = ErrorCode.SCRIM_REQUEST_IS_NOT_FOUND.desc
        )
    }

    fun search(
        queryFilter: ScrimRequestQueryFilter,
        pagination: Pagination,
        orderTypes: List<ScrimRequestOrderType>?
    ): List<ScrimRequest> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: ScrimRequestQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<ScrimRequestOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                ScrimRequestOrderType.CREATED_AT_ASC -> scrimRequest.createdAt.asc()
                ScrimRequestOrderType.CREATED_AT_DESC -> scrimRequest.createdAt.desc()
            }
        }.toTypedArray()
    }
}
