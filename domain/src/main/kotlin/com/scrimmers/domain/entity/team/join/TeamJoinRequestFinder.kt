package com.scrimmers.domain.entity.team.join

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.join.QTeamJoinRequest.teamJoinRequest
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class TeamJoinRequestFinder(
    private val repository: TeamJoinRequestRepository,
    private val customRepository: TeamJoinRequestCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): TeamJoinRequest {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.TEAM_JOIN_REQUEST_IS_NOT_FOUND,
            message = ErrorCode.TEAM_JOIN_REQUEST_IS_NOT_FOUND.desc
        )
    }

    fun search(
        queryFilter: TeamJoinRequestQueryFilter,
        pagination: Pagination,
        orderTypes: List<TeamJoinRequestOrderType>?
    ): List<TeamJoinRequest> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: TeamJoinRequestQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<TeamJoinRequestOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                TeamJoinRequestOrderType.CREATED_AT_ASC -> teamJoinRequest.createdAt.asc()
                TeamJoinRequestOrderType.CREATED_AT_DESC -> teamJoinRequest.createdAt.desc()
            }
        }.toTypedArray()
    }
}
