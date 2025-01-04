package com.scrimmers.api.service.team

import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.dto.team.CreateTeamRequestDto
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.api.dto.team.UpdateTeamRequestDto
import com.scrimmers.api.service.team.converter.TeamConverter
import com.scrimmers.api.service.team.updater.TeamUpdater
import com.scrimmers.api.service.team.validator.TeamValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.entity.team.TeamOrderType
import com.scrimmers.domain.entity.team.TeamQueryFilter
import com.scrimmers.domain.entity.team.TeamRepository
import com.scrimmers.domain.entity.user.User
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.Throws

@Service
class TeamService(
    private val repository: TeamRepository,
    private val finder: TeamFinder,
    private val userFinder: UserFinder,
    private val converter: TeamConverter,
    private val validator: TeamValidator,
    private val updater: TeamUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateTeamRequestDto): String {
        validator.validate(request)
        val user = userFinder.findById(userId)
        validateUserTeam(user)
        validateTeamNameDuplicated(request.name)
        val team = converter.convert(request).also {
            it.setBy(user)
        }
        val savedTeam = repository.save(team)
        user.setBy(savedTeam)
        return savedTeam.id
    }

    @Transactional(readOnly = true)
    fun getTeams(
        queryFilter: TeamQueryFilter,
        pagination: Pagination,
        orderTypes: List<TeamOrderType>?
    ): PaginationResponseDto {
        val teams = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = teams.map { converter.convert(it) }
        )
    }

    @Transactional(readOnly = true)
    fun getTeam(teamId: String): TeamResponseDto {
        val team = finder.findById(teamId)
        return converter.convert(team)
    }

    @Transactional
    fun update(userId: String, teamId: String, request: UpdateTeamRequestDto): String {
        validator.validate(request)
        val team = finder.findById(id = teamId)
        validateOwner(
            userId = userId,
            team = team
        )
        validateTeamNameDuplicated(request.name!!)
        updater.markAsUpdate(
            request = request,
            entity = team
        )
        return team.id
    }

    @Transactional
    fun delete(userId: String, teamId: String): Boolean {
        val team = finder.findById(id = teamId)
        validateOwner(
            userId = userId,
            team = team
        )
        userFinder.findByTeamId(team.id).forEach {
            it.leaveTeam()
        }
        team.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateOwner(userId: String, team: Team) {
        if (team.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateUserTeam(user: User) {
        if (user.team != null) {
            throw PolicyException(
                errorCode = ErrorCode.USER_ALREADY_HAVE_TEAM,
                message = ErrorCode.USER_ALREADY_HAVE_TEAM.desc
            )
        }
    }

    @Throws(PolicyException::class)
    private fun validateTeamNameDuplicated(teamName: String) {
        val isExists = finder.existsByName(teamName.lowercase())
        if (isExists) {
            throw PolicyException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "이미 존재하는 팀명입니다."
            )
        }
    }
}
