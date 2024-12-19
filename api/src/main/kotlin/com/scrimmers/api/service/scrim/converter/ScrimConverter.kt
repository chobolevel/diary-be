package com.scrimmers.api.service.scrim.converter

import com.scrimmers.api.dto.scrim.CreateScrimRequestDto
import com.scrimmers.api.dto.scrim.ScrimResponseDto
import com.scrimmers.api.service.team.converter.TeamConverter
import com.scrimmers.domain.entity.team.scrim.Scrim
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ScrimConverter(
    private val scrimRequestConverter: ScrimRequestConverter,
    private val teamConverter: TeamConverter
) {

    fun convert(request: CreateScrimRequestDto): Scrim {
        return Scrim(
            id = TSID.fast().toString(),
            type = request.type,
            name = request.name,
            startedAt = request.startedAt
        )
    }

    fun convert(entity: Scrim): ScrimResponseDto {
        return ScrimResponseDto(
            id = entity.id,
            scrimRequest = scrimRequestConverter.convert(entity.scrimRequest!!),
            homeTeam = teamConverter.convert(entity.homeTeam!!),
            awayTeam = teamConverter.convert(entity.awayTeam!!),
            type = entity.type,
            name = entity.name,
            startedAt = entity.startedAt,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }
}
