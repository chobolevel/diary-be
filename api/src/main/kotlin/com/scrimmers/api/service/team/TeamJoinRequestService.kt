package com.scrimmers.api.service.team

import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.dto.team.join.CreateTeamJoinRequestDto
import com.scrimmers.api.dto.team.join.RejectTeamJoinRequestDto
import com.scrimmers.api.dto.team.join.TeamJoinRequestResponseDto
import com.scrimmers.api.dto.team.join.UpdateTeamJoinRequestDto
import com.scrimmers.api.service.team.converter.TeamJoinRequestConverter
import com.scrimmers.api.service.team.updater.TeamJoinRequestUpdater
import com.scrimmers.api.service.team.validator.TeamJoinRequestValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.entity.team.join.TeamJoinRequest
import com.scrimmers.domain.entity.team.join.TeamJoinRequestFinder
import com.scrimmers.domain.entity.team.join.TeamJoinRequestOrderType
import com.scrimmers.domain.entity.team.join.TeamJoinRequestQueryFilter
import com.scrimmers.domain.entity.team.join.TeamJoinRequestRepository
import com.scrimmers.domain.entity.team.join.TeamJoinRequestStatus
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamJoinRequestService(
    private val repository: TeamJoinRequestRepository,
    private val finder: TeamJoinRequestFinder,
    private val teamFinder: TeamFinder,
    private val userFinder: UserFinder,
    private val converter: TeamJoinRequestConverter,
    private val validator: TeamJoinRequestValidator,
    private val updater: TeamJoinRequestUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateTeamJoinRequestDto): String {
        validator.validate(request)
        val team = teamFinder.findById(request.teamId)
        val user = userFinder.findById(userId)
        val teamJoinRequest = converter.convert(request).also {
            it.setBy(team)
            it.setBy(user)
        }
        user.setBy(team)
        return repository.save(teamJoinRequest).id
    }

    @Transactional(readOnly = true)
    fun getTeamJoinRequests(
        queryFilter: TeamJoinRequestQueryFilter,
        pagination: Pagination,
        orderTypes: List<TeamJoinRequestOrderType>?
    ): PaginationResponseDto {
        val teamJoinRequests = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = teamJoinRequests.map { converter.convert(it) }
        )
    }

    @Transactional(readOnly = true)
    fun getTeamJoinRequest(id: String): TeamJoinRequestResponseDto {
        val teamJoinRequest = finder.findById(id)
        return converter.convert(teamJoinRequest)
    }

    @Transactional
    fun update(userId: String, teamJoinRequestId: String, request: UpdateTeamJoinRequestDto): String {
        validator.validate(request)
        val teamJoinRequest = finder.findById(teamJoinRequestId)
        validateRequester(
            userId = userId,
            teamJoinRequest = teamJoinRequest
        )
        validateStatus(teamJoinRequest)
        updater.markAsUpdate(
            request = request,
            entity = teamJoinRequest
        )
        return teamJoinRequest.id
    }

    @Transactional
    fun approve(userId: String, teamJoinRequestId: String): String {
        val teamJoinRequest = finder.findById(teamJoinRequestId)
        validateTeamOwner(
            userId = userId,
            teamJoinRequest = teamJoinRequest
        )
        validateStatus(teamJoinRequest)
        teamJoinRequest.status = TeamJoinRequestStatus.APPROVED
        teamJoinRequest.user!!.setBy(teamJoinRequest.team!!)
        return teamJoinRequest.id
    }

    @Transactional
    fun reject(userId: String, teamJoinRequestId: String, request: RejectTeamJoinRequestDto): String {
        val teamJoinRequest = finder.findById(teamJoinRequestId)
        validateTeamOwner(
            userId = userId,
            teamJoinRequest = teamJoinRequest
        )
        validateStatus(teamJoinRequest)
        teamJoinRequest.status = TeamJoinRequestStatus.REJECTED
        teamJoinRequest.rejectReason = request.reason
        return teamJoinRequest.id
    }

    @Transactional
    fun delete(userId: String, teamJoinRequestId: String): Boolean {
        val teamJoinRequest = finder.findById(teamJoinRequestId)
        validateRequester(
            userId = userId,
            teamJoinRequest = teamJoinRequest
        )
        validateStatus(teamJoinRequest)
        teamJoinRequest.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateRequester(userId: String, teamJoinRequest: TeamJoinRequest) {
        if (teamJoinRequest.user!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_REQUESTER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_REQUESTER.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateStatus(teamJoinRequest: TeamJoinRequest) {
        if (teamJoinRequest.status != TeamJoinRequestStatus.REQUESTED) {
            throw PolicyException(
                errorCode = ErrorCode.TEAM_JOIN_REQUEST_IS_ALREADY_PROCESSED,
                message = ErrorCode.TEAM_JOIN_REQUEST_IS_ALREADY_PROCESSED.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateTeamOwner(userId: String, teamJoinRequest: TeamJoinRequest) {
        if (teamJoinRequest.team!!.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }
}
