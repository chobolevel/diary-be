package com.scrimmers.domain.entity.team

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.QTeam.team
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class TeamFinder(
    private val repository: TeamRepository,
    private val customRepository: TeamCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): Team {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.TEAM_IS_NOT_FOUND,
            message = ErrorCode.TEAM_IS_NOT_FOUND.desc
        )
    }

    @Throws(DataNotFoundException::class)
    fun findByIdAndOwnerId(id: String, ownerId: String): Team {
        return repository.findByIdAndOwnerIdAndDeletedFalse(
            id = id,
            ownerId = ownerId
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.TEAM_IS_NOT_FOUND,
            message = ErrorCode.TEAM_IS_NOT_FOUND.desc
        )
    }

    fun search(queryFilter: TeamQueryFilter, pagination: Pagination, orderTypes: List<TeamOrderType>?): List<Team> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: TeamQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<TeamOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                TeamOrderType.CREATED_AT_ASC -> team.createdAt.asc()
                TeamOrderType.CREATED_AT_DESC -> team.createdAt.desc()
            }
        }.toTypedArray()
    }
}
