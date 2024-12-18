package com.scrimmers.api.service.team.converter

import com.scrimmers.api.dto.team.CreateTeamRequestDto
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.domain.entity.team.Team
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class TeamConverter(
    private val teamImageConverter: TeamImageConverter
) {

    fun convert(request: CreateTeamRequestDto): Team {
        return Team(
            id = TSID.fast().toString(),
            name = request.name,
            description = request.description,
        )
    }

    fun convert(entity: Team): TeamResponseDto {
        return TeamResponseDto(
            id = entity.id,
            ownerId = entity.owner!!.id,
            ownerNickname = entity.owner!!.nickname,
            name = entity.name,
            description = entity.description,
            logo = teamImageConverter.convert(entity.teamImage),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }
}
