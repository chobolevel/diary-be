package com.scrimmers.domain.entity.team.leave

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.leave.QTeamLeaveRequest.teamLeaveRequest
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TeamLeaveRequestFinder(
    private val repository: TeamLeaveRequestRepository,
    private val customRepository: TeamLeaveRequestCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): TeamLeaveRequest {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.TEAM_LEAVE_REQUEST_IS_NOT_FOUND,
            status = HttpStatus.NOT_FOUND,
            message = ErrorCode.TEAM_LEAVE_REQUEST_IS_NOT_FOUND.desc
        )
    }

    fun search(
        queryFilter: TeamLeaveRequestQueryFilter,
        pagination: Pagination,
        orderTypes: List<TeamLeaveRequestOrderType>?
    ): List<TeamLeaveRequest> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: TeamLeaveRequestQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<TeamLeaveRequestOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                TeamLeaveRequestOrderType.CREATED_AT_ASC -> teamLeaveRequest.createdAt.asc()
                TeamLeaveRequestOrderType.CREATED_AT_DESC -> teamLeaveRequest.createdAt.desc()
            }
        }.toTypedArray()
    }
}
