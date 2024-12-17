package com.scrimmers.api.service.team.validator

import com.scrimmers.api.dto.team.UpdateTeamRequestDto
import com.scrimmers.domain.entity.team.TeamUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TeamValidator {

    fun validate(request: UpdateTeamRequestDto) {
        request.updateMask.forEach {
            when (it) {
                TeamUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 팀명이 유효하지 않습니다.",
                        )
                    }
                }

                TeamUpdateMask.DESCRIPTION -> {
                    if (request.description.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경한 팀 설명이 유효하지 않습니다."
                        )
                    }
                }
            }
        }
    }
}
