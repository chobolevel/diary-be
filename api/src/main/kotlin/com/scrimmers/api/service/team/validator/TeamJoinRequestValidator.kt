package com.scrimmers.api.service.team.validator

import com.scrimmers.api.dto.team.join.CreateTeamJoinRequestDto
import com.scrimmers.api.dto.team.join.UpdateTeamJoinRequestDto
import com.scrimmers.domain.entity.team.join.TeamJoinRequestUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component

@Component
class TeamJoinRequestValidator {

    @Throws(ParameterInvalidException::class)
    fun validate(request: CreateTeamJoinRequestDto) {
        if (request.comment.length < 10) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "소개 글은 최소 10자 이상이어야 합니다."
            )
        }
    }

    @Throws(ParameterInvalidException::class)
    fun validate(request: UpdateTeamJoinRequestDto) {
        request.updateMask.forEach {
            when (it) {
                TeamJoinRequestUpdateMask.COMMENT -> {
                    if (request.comment.isNullOrEmpty() || request.comment.length < 10) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 소개 글이 유효하지 않습니다.(최소 10자 이상)"
                        )
                    }
                }
            }
        }
    }
}
