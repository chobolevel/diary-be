package com.scrimmers.api.service.team

import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.dto.team.leave.CreateTeamLeaveRequestDto
import com.scrimmers.api.dto.team.leave.RejectTeamLeaveRequestDto
import com.scrimmers.api.dto.team.leave.TeamLeaveRequestResponseDto
import com.scrimmers.api.dto.team.leave.UpdateTeamLeaveRequestDto
import com.scrimmers.api.service.team.converter.TeamLeaveRequestConverter
import com.scrimmers.api.service.team.updater.TeamLeaveRequestUpdater
import com.scrimmers.api.service.team.validator.TeamLeaveRequestValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequest
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestFinder
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestOrderType
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestQueryFilter
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestRepository
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestStatus
import com.scrimmers.domain.entity.user.User
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamLeaveRequestService(
    private val repository: TeamLeaveRequestRepository,
    private val finder: TeamLeaveRequestFinder,
    private val teamFinder: TeamFinder,
    private val userFinder: UserFinder,
    private val converter: TeamLeaveRequestConverter,
    private val validator: TeamLeaveRequestValidator,
    private val updater: TeamLeaveRequestUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateTeamLeaveRequestDto): String {
        validator.validate(request)
        val user = userFinder.findById(userId)
        val team = teamFinder.findById(request.teamId)
        validateTeamMember(
            user = user,
            team = team
        )
        val teamLeaveRequest = converter.convert(request).also {
            it.setBy(team)
            it.setBy(user)
        }
        return repository.save(teamLeaveRequest).id
    }

    @Transactional(readOnly = true)
    fun getTeamLeaveRequests(
        queryFilter: TeamLeaveRequestQueryFilter,
        pagination: Pagination,
        orderTypes: List<TeamLeaveRequestOrderType>?
    ): PaginationResponseDto {
        val teamLeaveRequests = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes,
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = teamLeaveRequests.map { converter.convert(it) }
        )
    }

    @Transactional(readOnly = true)
    fun getTeamLeaveRequest(teamLeaveRequestId: String): TeamLeaveRequestResponseDto {
        val teamLeaveRequest = finder.findById(teamLeaveRequestId)
        return converter.convert(teamLeaveRequest)
    }

    @Transactional
    fun update(userId: String, teamLeaveRequestId: String, request: UpdateTeamLeaveRequestDto): String {
        validator.validate(request)
        val teamLeaveRequest = finder.findById(teamLeaveRequestId)
        validateStatus(teamLeaveRequest)
        validateRequester(
            userId = userId,
            teamLeaveRequest = teamLeaveRequest
        )
        updater.markAsUpdate(
            request = request,
            entity = teamLeaveRequest
        )
        return teamLeaveRequest.id
    }

    @Transactional
    fun approve(userId: String, teamLeaveRequestId: String): String {
        val teamLeaveRequest = finder.findById(teamLeaveRequestId)
        validateStatus(teamLeaveRequest)
        validateTeamOwner(
            userId = userId,
            teamLeaveRequest = teamLeaveRequest
        )
        teamLeaveRequest.status = TeamLeaveRequestStatus.APPROVED
        teamLeaveRequest.user!!.leaveTeam()
        teamLeaveRequest.team!!.decreaseHeadCount()
        return teamLeaveRequest.id
    }

    @Transactional
    fun reject(userId: String, teamLeaveRequestId: String, request: RejectTeamLeaveRequestDto): String {
        val teamLeaveRequest = finder.findById(teamLeaveRequestId)
        validateStatus(teamLeaveRequest)
        validateTeamOwner(
            userId = userId,
            teamLeaveRequest = teamLeaveRequest
        )
        teamLeaveRequest.status = TeamLeaveRequestStatus.REJECTED
        teamLeaveRequest.rejectReason = request.rejectReason
        return teamLeaveRequest.id
    }

    @Transactional
    fun delete(userId: String, teamLeaveRequestId: String): Boolean {
        val teamLeaveRequest = finder.findById(teamLeaveRequestId)
        validateStatus(teamLeaveRequest)
        validateRequester(
            userId = userId,
            teamLeaveRequest = teamLeaveRequest
        )
        teamLeaveRequest.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateTeamMember(user: User, team: Team) {
        if (user.team!!.id != team.id) {
            throw PolicyException(
                errorCode = ErrorCode.TEAM_LEAVE_REQUEST_IS_ONLY_FOR_TEAM_MEMBER,
                message = ErrorCode.TEAM_LEAVE_REQUEST_IS_ONLY_FOR_TEAM_MEMBER.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateRequester(userId: String, teamLeaveRequest: TeamLeaveRequest) {
        if (teamLeaveRequest.user!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_REQUESTER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_REQUESTER.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateStatus(teamLeaveRequest: TeamLeaveRequest) {
        if (teamLeaveRequest.status != TeamLeaveRequestStatus.REQUESTED) {
            throw PolicyException(
                errorCode = ErrorCode.TEAM_LEAVE_REQUEST_IS_ALREADY_PROCESSED,
                message = ErrorCode.TEAM_LEAVE_REQUEST_IS_ALREADY_PROCESSED.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateTeamOwner(userId: String, teamLeaveRequest: TeamLeaveRequest) {
        if (teamLeaveRequest.team!!.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }
}
