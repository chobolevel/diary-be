package com.scrimmers.api.service.scrim

import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.dto.scrim.request.CreateScrimRequestRequestDto
import com.scrimmers.api.dto.scrim.request.RejectScrimRequestRequestDto
import com.scrimmers.api.dto.scrim.request.ScrimRequestResponseDto
import com.scrimmers.api.dto.scrim.request.UpdateScrimRequestRequestDto
import com.scrimmers.api.service.scrim.converter.ScrimRequestConverter
import com.scrimmers.api.service.scrim.updater.ScrimRequestUpdater
import com.scrimmers.api.service.scrim.validator.ScrimRequestValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.scrim.request.ScrimRequest
import com.scrimmers.domain.entity.scrim.request.ScrimRequestFinder
import com.scrimmers.domain.entity.scrim.request.ScrimRequestOrderType
import com.scrimmers.domain.entity.scrim.request.ScrimRequestQueryFilter
import com.scrimmers.domain.entity.scrim.request.ScrimRequestRepository
import com.scrimmers.domain.entity.scrim.request.ScrimRequestStatus
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class ScrimRequestService(
    private val repository: ScrimRequestRepository,
    private val finder: ScrimRequestFinder,
    private val teamFinder: TeamFinder,
    private val converter: ScrimRequestConverter,
    private val validator: ScrimRequestValidator,
    private val updater: ScrimRequestUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateScrimRequestRequestDto): String {
        validator.validate(request)
        val fromTeam = teamFinder.findById(request.fromTeamId)
        val toTeam = teamFinder.findById(request.toTeamId)
        validateFromTeamOwner(
            userId = userId,
            fromTeam = fromTeam
        )
        val scrimRequest = converter.convert(request).also {
            it.initFromTeam(fromTeam)
            it.initToTeam(toTeam)
        }
        return repository.save(scrimRequest).id
    }

    @Transactional(readOnly = true)
    fun getScrimRequests(
        queryFilter: ScrimRequestQueryFilter,
        pagination: Pagination,
        orderTypes: List<ScrimRequestOrderType>?
    ): PaginationResponseDto {
        val scrimRequests = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes,
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = scrimRequests.map { converter.convert(it) }
        )
    }

    @Transactional(readOnly = true)
    fun getScrimRequest(scrimRequestId: String): ScrimRequestResponseDto {
        val scrimRequest = finder.findById(scrimRequestId)
        return converter.convert(scrimRequest)
    }

    @Transactional
    fun update(userId: String, scrimRequestId: String, request: UpdateScrimRequestRequestDto): String {
        validator.validate(request)
        val scrimRequest = finder.findById(scrimRequestId)
        validateStatus(scrimRequest)
        validateFromTeamOwner(
            userId = userId,
            fromTeam = scrimRequest.fromTeam!!
        )
        updater.markAsUpdate(
            request = request,
            entity = scrimRequest
        )
        return scrimRequest.id
    }

    @Transactional
    fun approve(userId: String, scrimRequestId: String): String {
        val scrimRequest = finder.findById(scrimRequestId)
        validateStatus(scrimRequest)
        validateToTeamOwner(
            userId = userId,
            toTeam = scrimRequest.toTeam!!
        )
        scrimRequest.status = ScrimRequestStatus.APPROVED
        return scrimRequest.id
    }

    @Transactional
    fun reject(userId: String, scrimRequestId: String, request: RejectScrimRequestRequestDto): String {
        val scrimRequest = finder.findById(scrimRequestId)
        validateStatus(scrimRequest)
        validateToTeamOwner(
            userId = userId,
            toTeam = scrimRequest.toTeam!!
        )
        scrimRequest.status = ScrimRequestStatus.REJECTED
        scrimRequest.rejectReason = request.rejectReason
        return scrimRequest.id
    }

    @Transactional
    fun delete(userId: String, scrimRequestId: String): Boolean {
        val scrimRequest = finder.findById(scrimRequestId)
        validateStatus(scrimRequest)
        validateFromTeamOwner(
            userId = userId,
            fromTeam = scrimRequest.fromTeam!!
        )
        scrimRequest.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateFromTeamOwner(userId: String, fromTeam: Team) {
        if (fromTeam.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateToTeamOwner(userId: String, toTeam: Team) {
        if (toTeam.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateStatus(scrimRequest: ScrimRequest) {
        if (scrimRequest.status != ScrimRequestStatus.REQUESTED) {
            throw PolicyException(
                errorCode = ErrorCode.SCRIM_REQUEST_IS_ALREADY_PROCESSED,
                message = ErrorCode.SCRIM_REQUEST_IS_NOT_FOUND.desc
            )
        }
    }
}
