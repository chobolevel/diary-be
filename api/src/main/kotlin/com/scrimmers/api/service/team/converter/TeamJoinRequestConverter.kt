package com.scrimmers.api.service.team.converter

import com.scrimmers.api.dto.team.join.CreateTeamJoinRequestDto
import com.scrimmers.api.dto.team.join.TeamJoinRequestResponseDto
import com.scrimmers.api.service.user.converter.UserConverter
import com.scrimmers.domain.entity.team.join.TeamJoinRequest
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class TeamJoinRequestConverter(
    private val teamConverter: TeamConverter,
    private val userConverter: UserConverter
) {

    fun convert(request: CreateTeamJoinRequestDto): TeamJoinRequest {
        return TeamJoinRequest(
            id = TSID.fast().toString(),
            comment = request.comment
        )
    }

    fun convert(entity: TeamJoinRequest): TeamJoinRequestResponseDto {
        return TeamJoinRequestResponseDto(
            id = entity.id,
            team = teamConverter.convert(entity.team!!),
            user = userConverter.convert(entity.user!!),
            status = entity.status,
            comment = entity.comment,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
