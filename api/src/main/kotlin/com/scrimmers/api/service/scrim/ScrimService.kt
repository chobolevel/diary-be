package com.scrimmers.api.service.scrim

import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.dto.scrim.CreateScrimRequestDto
import com.scrimmers.api.dto.scrim.ScrimResponseDto
import com.scrimmers.api.dto.scrim.UpdateScrimRequestDto
import com.scrimmers.api.service.scrim.converter.ScrimConverter
import com.scrimmers.api.service.scrim.updater.ScrimUpdater
import com.scrimmers.api.service.scrim.validator.ScrimValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.scrim.ScrimFinder
import com.scrimmers.domain.entity.scrim.ScrimOrderType
import com.scrimmers.domain.entity.scrim.ScrimQueryFilter
import com.scrimmers.domain.entity.scrim.ScrimRepository
import com.scrimmers.domain.entity.scrim.request.ScrimRequestFinder
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class ScrimService(
    private val repository: ScrimRepository,
    private val finder: ScrimFinder,
    private val scrimRequestFinder: ScrimRequestFinder,
    private val teamFinder: TeamFinder,
    private val converter: ScrimConverter,
    private val validator: ScrimValidator,
    private val updater: ScrimUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateScrimRequestDto): String {
        validator.validate(request)
        val scrimRequest = scrimRequestFinder.findById(request.scrimRequestId)
        val homeTeam = teamFinder.findById(request.homeTeamId)
        val awayTeam = teamFinder.findById(request.awayTeamId)
        validateHomeOrAwayTeamOwner(
            userId = userId,
            homeTeam = homeTeam,
            awayTeam = awayTeam
        )
        val scrim = converter.convert(request).also {
            it.setBy(scrimRequest)
            it.initHomeTeam(homeTeam)
            it.initAwayTeam(awayTeam)
        }
        return repository.save(scrim).id
    }

    @Transactional(readOnly = true)
    fun getScrims(
        queryFilter: ScrimQueryFilter,
        pagination: Pagination,
        orderTypes: List<ScrimOrderType>?
    ): PaginationResponseDto {
        val scrims = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes,
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = scrims.map { converter.convert(it) }
        )
    }

    @Transactional(readOnly = true)
    fun getScrim(scrimId: String): ScrimResponseDto {
        val scrim = finder.findById(scrimId)
        return converter.convert(scrim)
    }

    @Transactional
    fun update(userId: String, scrimId: String, request: UpdateScrimRequestDto): String {
        validator.validate(request)
        val scrim = finder.findById(scrimId)
        validateHomeOrAwayTeamOwner(
            userId = userId,
            homeTeam = scrim.homeTeam!!,
            awayTeam = scrim.awayTeam!!,
        )
        updater.markAsUpdate(
            request = request,
            entity = scrim
        )
        return scrim.id
    }

    @Transactional
    fun delete(userId: String, scrimId: String): Boolean {
        val scrim = finder.findById(scrimId)
        validateHomeOrAwayTeamOwner(
            userId = userId,
            homeTeam = scrim.homeTeam!!,
            awayTeam = scrim.awayTeam!!,
        )
        scrim.delete()
        return true
    }

    @Throws
    private fun validateHomeOrAwayTeamOwner(userId: String, homeTeam: Team, awayTeam: Team) {
        if (homeTeam.owner!!.id != userId && awayTeam.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }
}
