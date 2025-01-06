package com.scrimmers.api.service.team.converter

import com.scrimmers.api.dto.team.leave.CreateTeamLeaveRequestDto
import com.scrimmers.api.dto.team.leave.TeamLeaveRequestResponseDto
import com.scrimmers.api.service.user.converter.UserConverter
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequest
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class TeamLeaveRequestConverter(
    private val teamConverter: TeamConverter,
    private val userConverter: UserConverter
) {

    fun convert(request: CreateTeamLeaveRequestDto): TeamLeaveRequest {
        return TeamLeaveRequest(
            id = TSID.fast().toString(),
            comment = request.comment
        )
    }

    fun convert(entity: TeamLeaveRequest): TeamLeaveRequestResponseDto {
        return TeamLeaveRequestResponseDto(
            id = entity.id,
            team = teamConverter.convert(entity.team!!),
            requester = userConverter.convert(entity.user!!),
            status = entity.status,
            comment = entity.comment,
            rejectReason = entity.rejectReason,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
