package com.scrimmers.api.service.scrim.converter

import com.scrimmers.api.dto.scrim.match.CreateScrimMatchRequestDto
import com.scrimmers.api.dto.scrim.match.ScrimMatchResponseDto
import com.scrimmers.domain.entity.team.scrim.match.ScrimMatch
import com.scrimmers.domain.entity.team.scrim.match.side.ScrimMatchSideType
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ScrimMatchConverter(
    private val scrimMatchSideConverter: ScrimMatchSideConverter
) {

    fun convert(request: CreateScrimMatchRequestDto): ScrimMatch {
        return ScrimMatch(
            id = TSID.fast().toString(),
            winnerSide = request.winnerSide,
        )
    }

    fun convert(entity: ScrimMatch): ScrimMatchResponseDto {
        return ScrimMatchResponseDto(
            id = entity.id,
            winnerSide = entity.winnerSide,
            blueSide = scrimMatchSideConverter.convert(
                entity.scrimMatchSides.find { it.side == ScrimMatchSideType.BLUE }!!,
                entity.winnerSide
            ),
            redSide = scrimMatchSideConverter.convert(
                entity.scrimMatchSides.find { it.side == ScrimMatchSideType.RED }!!,
                entity.winnerSide
            ),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
