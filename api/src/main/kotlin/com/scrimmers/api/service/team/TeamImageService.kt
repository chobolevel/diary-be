package com.scrimmers.api.service.team

import com.scrimmers.api.dto.team.image.CreateTeamImageRequestDto
import com.scrimmers.api.dto.team.image.UpdateTeamImageRequestDto
import com.scrimmers.api.service.team.converter.TeamImageConverter
import com.scrimmers.api.service.team.updater.TeamImageUpdater
import com.scrimmers.api.service.team.validator.TeamImageValidator
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.entity.team.image.TeamImageFinder
import com.scrimmers.domain.entity.team.image.TeamImageRepository
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamImageService(
    private val repository: TeamImageRepository,
    private val finder: TeamImageFinder,
    private val teamFinder: TeamFinder,
    private val converter: TeamImageConverter,
    private val validator: TeamImageValidator,
    private val updater: TeamImageUpdater
) {

    @Transactional
    fun create(userId: String, teamId: String, request: CreateTeamImageRequestDto): String {
        val team = teamFinder.findById(teamId)
        validateOwner(
            userId = userId,
            team = team
        )
        val teamImage = converter.convert(request).also {
            it.setBy(team)
        }
        return repository.save(teamImage).id
    }

    @Transactional
    fun update(userId: String, teamId: String, teamImageId: String, request: UpdateTeamImageRequestDto): String {
        validator.validate(request)
        val teamImage = finder.findById(teamImageId)
        validateOwner(
            userId = userId,
            team = teamImage.team!!
        )
        updater.markAsUpdate(
            request = request,
            entity = teamImage
        )
        return teamImage.id
    }

    @Transactional
    fun delete(userId: String, teamId: String, teamImageId: String): Boolean {
        val teamImage = finder.findById(teamImageId)
        validateOwner(
            userId = userId,
            team = teamImage.team!!
        )
        teamImage.delete()
        return true
    }

    private fun validateOwner(userId: String, team: Team) {
        if (team.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }
}
