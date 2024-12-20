package com.scrimmers.api.service.scrim.converter

import com.scrimmers.api.dto.scrim.CreateScrimRequestDto
import com.scrimmers.api.dto.scrim.ScrimResponseDto
import com.scrimmers.api.service.team.converter.TeamConverter
import com.scrimmers.domain.entity.team.scrim.Scrim
import com.scrimmers.domain.entity.team.scrim.match.ScrimMatchWinnerSide
import com.scrimmers.domain.entity.team.scrim.match.side.ScrimMatchSideType
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ScrimConverter(
    private val scrimRequestConverter: ScrimRequestConverter,
    private val teamConverter: TeamConverter,
    private val scrimMatchConverter: ScrimMatchConverter
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
        val winnerSides = entity.scrimMatches.map { scrimMatch ->
            when (scrimMatch.winnerSide) {
                ScrimMatchWinnerSide.BLUE -> scrimMatch.scrimMatchSides.find { it.side == ScrimMatchSideType.BLUE }
                ScrimMatchWinnerSide.RED -> scrimMatch.scrimMatchSides.find { it.side == ScrimMatchSideType.RED }
            }
        }
        return ScrimResponseDto(
            id = entity.id,
            scrimRequest = scrimRequestConverter.convert(entity.scrimRequest!!),
            homeTeam = teamConverter.convert(entity.homeTeam!!),
            homeTeamScore = winnerSides.filter { it!!.team!!.id == entity.homeTeam!!.id }.size,
            awayTeam = teamConverter.convert(entity.awayTeam!!),
            awayTeamScore = winnerSides.filter { it!!.team!!.id == entity.awayTeam!!.id }.size,
            type = entity.type,
            name = entity.name,
            startedAt = entity.startedAt,
            matches = entity.scrimMatches.map { scrimMatchConverter.convert(it) },
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }
}
