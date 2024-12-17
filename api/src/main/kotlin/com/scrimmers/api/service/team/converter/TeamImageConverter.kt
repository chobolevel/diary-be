package com.scrimmers.api.service.team.converter

import com.scrimmers.api.dto.team.image.CreateTeamImageRequestDto
import com.scrimmers.api.dto.team.image.TeamImageResponseDto
import com.scrimmers.domain.entity.team.image.TeamImage
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class TeamImageConverter {

    fun convert(request: CreateTeamImageRequestDto): TeamImage {
        return TeamImage(
            id = TSID.fast().toString(),
            type = request.type,
            name = request.name,
            url = request.url,
        )
    }

    fun convert(entity: TeamImage?): TeamImageResponseDto? {
        if (entity == null) {
            return null
        }
        return TeamImageResponseDto(
            id = entity.id,
            type = entity.type,
            name = entity.name,
            url = entity.url,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }
}
