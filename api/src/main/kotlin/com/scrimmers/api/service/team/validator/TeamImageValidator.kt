package com.scrimmers.api.service.team.validator

import com.scrimmers.api.dto.team.image.UpdateTeamImageRequestDto
import com.scrimmers.domain.entity.team.image.TeamImageUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TeamImageValidator {

    fun validate(request: UpdateTeamImageRequestDto) {
        request.updateMask.forEach {
            when (it) {
                TeamImageUpdateMask.TYPE -> {
                    if (request.type == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 이미지 유형이 유효하지 않습니다."
                        )
                    }
                }

                TeamImageUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 이미지 파일명이 유효하지 않습니다."
                        )
                    }
                }

                TeamImageUpdateMask.URL -> {
                    if (request.url.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 이미지 경로가 유효하지 않습니다."
                        )
                    }
                }
            }
        }
    }
}
