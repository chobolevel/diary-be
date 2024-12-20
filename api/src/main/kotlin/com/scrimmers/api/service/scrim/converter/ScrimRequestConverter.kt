package com.scrimmers.api.service.scrim.converter

import com.scrimmers.api.dto.scrim.request.CreateScrimRequestRequestDto
import com.scrimmers.api.dto.scrim.request.ScrimRequestResponseDto
import com.scrimmers.api.service.team.converter.TeamConverter
import com.scrimmers.domain.entity.scrim.request.ScrimRequest
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ScrimRequestConverter(
    private val teamConverter: TeamConverter
) {

    fun convert(request: CreateScrimRequestRequestDto): ScrimRequest {
        return ScrimRequest(
            id = TSID.fast().toString(),
            comment = request.comment
        )
    }

    fun convert(entity: ScrimRequest): ScrimRequestResponseDto {
        return ScrimRequestResponseDto(
            id = entity.id,
            fromTeam = teamConverter.convert(entity.fromTeam!!),
            toTeam = teamConverter.convert(entity.toTeam!!),
            status = entity.status,
            comment = entity.comment,
            rejectReason = entity.rejectReason,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }
}
