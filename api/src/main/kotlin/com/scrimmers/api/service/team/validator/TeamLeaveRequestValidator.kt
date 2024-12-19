package com.scrimmers.api.service.team.validator

import com.scrimmers.api.dto.team.leave.CreateTeamLeaveRequestDto
import com.scrimmers.api.dto.team.leave.UpdateTeamLeaveRequestDto
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component

@Component
class TeamLeaveRequestValidator {

    @Throws(ParameterInvalidException::class)
    fun validate(request: CreateTeamLeaveRequestDto) {
        if (request.comment.length < 10) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "탈퇴 사유는 최소 10자 이상이어야 합니다."
            )
        }
    }

    @Throws(ParameterInvalidException::class)
    fun validate(request: UpdateTeamLeaveRequestDto) {
        request.updateMask.forEach {
            when (it) {
                TeamLeaveRequestUpdateMask.COMMENT -> {
                    if (request.comment.isNullOrEmpty() || request.comment.length < 10) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 탈퇴 사유가 유효하지 않습니다.(최소 10자 이상)"
                        )
                    }
                }
            }
        }
    }
}
